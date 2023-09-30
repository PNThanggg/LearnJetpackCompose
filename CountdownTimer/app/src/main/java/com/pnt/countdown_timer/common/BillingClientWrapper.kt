package com.pnt.countdown_timer.common

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val RECONNECT_TIMER_START_MILLISECONDS = 1L * 1000L
private const val RECONNECT_TIMER_MAX_TIME_MILLISECONDS = 1000L * 60L * 15L // 15 minutes
private const val SKU_DETAILS_REQUERY_TIME = 1000L * 60L * 60L * 4L // 4 hours

class BillingClientWrapper(
    context: Context
) : PurchasesUpdatedListener {

    // future.txt refactor billingClient as class dependency
    private val billingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    // doitwrong: this is wack but it works
    private var purchasesUpdatedContinuation: Continuation<BillingResult>? = null

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        try {
            purchasesUpdatedContinuation?.resume(billingResult)
        } catch (e: RuntimeException) {
            // this happens sometimes for some reason ???
            Log.e("com.pnt.countdown_timer.common.BillingClientWrapper", "error: $e")
        }

        if (billingResult.responseCode != BillingClient.BillingResponseCode.OK || purchases.isNullOrEmpty()) {
            return
        }
        for (purchase in purchases) {
            acknowledgePurchase(purchase)
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        if (!purchase.isAcknowledged) {
            val params = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
            billingClient.acknowledgePurchase(
                params
            ) { billingResult ->
                Log.d("BillingClientWrapper", "acknowledgePurchase billingResult $billingResult")
            }
        }
    }

    suspend fun startBillingConnection(): BillingResult = suspendCoroutine { continuation ->
        val billingClientStateListener = object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                // probably best to let the app crash if this called???
                Log.d("BillingClientWrapper", "onBillingServiceDisconnected")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                continuation.resume(billingResult)
            }
        }
        billingClient.startConnection(billingClientStateListener)
    }

    // rewrite with my own result type, or arrow either
    // allow me to show snackbars on error cases

    // startBillingConnection must be called first
    suspend fun checkHaloColourPurchased(
    ): Boolean = suspendCoroutine { continuation ->
        val queryPurchasesParams =
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP)
                .build()

        val purchasesResponseListener =
            PurchasesResponseListener { billingResult: BillingResult, purchases: MutableList<Purchase> ->
                var purchased = false
                when (billingResult.responseCode) {
                    BillingClient.BillingResponseCode.OK -> {
                        for (purchase in purchases) {
                            if (purchase.products.contains("halo_colour")) {
                                purchased = true
                                break
                            }
                        }
                    }

                    else -> {
                    }
                }
                continuation.resume(purchased)
            }
        billingClient.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener)
    }


    fun endBillingConnection() {
        billingClient.endConnection()
    }

    suspend fun purchaseHaloColourChange(activity: Activity): BillingResult? {
        // do i need withContext(IO) ??? assume no

        val productDetails = getHaloColourProductDetails() ?: return null

        return suspendCoroutine { continuation ->
            // this is a hack, shouldn't be a problem if using fresh com.pnt.countdown_timer.common.BillingClientWrapper
            // for each call to purchaseHaloColourChange()
            purchasesUpdatedContinuation = continuation

            val productDetailsParams =
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .build()

            val params = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(listOf(productDetailsParams))
                .build()
            billingClient.launchBillingFlow(activity, params)
        }
    }


    suspend fun getHaloColourProductDetails(): ProductDetails? =
        suspendCoroutine { continuation ->
            val productId = "halo_colour"
            val product = QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()

            val params =
                QueryProductDetailsParams.newBuilder().setProductList(listOf(product)).build()

            val productDetailsResponseListener =
                ProductDetailsResponseListener { _, productDetailsList ->
                    val productDetails = productDetailsList.find {
                        it.productId == "halo_colour"
                    }
                    continuation.resume(productDetails)
                }
            billingClient.queryProductDetailsAsync(params, productDetailsResponseListener)
        }


    companion object {
        // use this function to start connection, run callback, end connection
        // poor design, future.txt use arrow.kt to deal with error handling
        suspend fun run(
            context: Context,
            errorMessageFlow: MutableSharedFlow<String>? = null,
            callback: suspend (BillingClientWrapper) -> Unit
        ) {
            val client = BillingClientWrapper(context)
            val result = client.startBillingConnection()
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                callback(client)
            } else {
                // i could move this into startBillingConnection
                errorMessageFlow?.emit(result.debugMessage)
            }
            client.endBillingConnection()
        }
    }
}