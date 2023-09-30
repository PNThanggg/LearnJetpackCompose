package com.pnt.caster

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

private const val TAG = "InterstitialAdManager"
private const val KEY_INTERSTITIALS = "ca-app-pub-3940256099942544/1033173712"

object InterstitialAdManager {
//    companion object {
//        private var instance: InterstitialAdManager? = null
//
//        fun getInstance(): InterstitialAdManager {
//            if (instance == null) {
//                synchronized(InterstitialAdManager::class.java) {
//                    if (instance == null) {
//                        instance = InterstitialAdManager()
//                    }
//                }
//            }
//            return instance!!
//        }
//    }

    private var mInterstitialAd: InterstitialAd? = null

    fun loadInterstitial(context: Context) {
        if (mInterstitialAd != null) return

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            KEY_INTERSTITIALS,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "onAdLoaded")
                    mInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(TAG, loadAdError.message)
                    mInterstitialAd = null
                }
            },
        )
    }

    fun showInterstitial(activity: Activity, onSuccess: () -> Unit) {
        if (mInterstitialAd == null) {
            loadInterstitial(activity)
            return
        }

        Log.d(TAG, "showInterstitial: start show Inter")

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null

                onSuccess.invoke()
                loadInterstitial(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                Log.e(TAG, adError.message)
                mInterstitialAd = null

                onSuccess.invoke()
                loadInterstitial(activity)
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        mInterstitialAd?.show(activity)
//        mInterstitialAd = null
    }
}

fun Activity.showInterstitial(onSuccess: () -> Unit) {
    Log.i("Activity", "showInterstitial()")
    InterstitialAdManager.showInterstitial(this, onSuccess = onSuccess)
}

fun Context.loadInterstitial() {
    InterstitialAdManager.loadInterstitial(this)
}