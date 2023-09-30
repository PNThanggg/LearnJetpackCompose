package com.pnt.back_press

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnt.back_press.ui.theme.BackPressJetpackTheme
import java.util.Stack


private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        

        setContent {
            BackPressJetpackTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }

            onBackPressedDispatcher.addCallback(
                callbackGenerator("MainActivity")
            )
        }
    }

    private fun callbackGenerator(title: String) = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.e(TAG, "handleOnBackPressed:  $title")
        }
    }
}

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val selected = remember { mutableStateOf(0) }
        val selectionStack = remember { Stack<Int>() }
        val handleBackHandler = remember(selected.value) {
            selectionStack.isNotEmpty().or(selected.value != 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            BackInvokeHandler(handleBackHandler) {
                selected.value = if (selectionStack.isEmpty()) 0 else selectionStack.pop()
            }
        } else {
            BackHandler(handleBackHandler) {
                selected.value = if (selectionStack.isEmpty()) 0 else selectionStack.pop()
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(64.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(24) { index ->
                BoxView(
                    text = "$index",
                    isSelected = selected.value == index,
                    onClick = fun() {
                        if (selected.value != index && !selectionStack.contains(selected.value)) {
                            selectionStack.push(selected.value)
                        }
                        selected.value = index
                    }
                )
            }
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun BackInvokeHandler(
    handleBackHandler: Boolean,
    priority: Int = OnBackInvokedDispatcher.PRIORITY_DEFAULT,
    callback: () -> Unit = {}
) {
    val backInvokedCallback = remember {
        OnBackInvokedCallback {
            callback()
        }
    }

    val activity = when (LocalLifecycleOwner.current) {
        is MainActivity -> LocalLifecycleOwner.current as MainActivity

        else -> {
            val context = LocalContext.current
            if (context is MainActivity) {
                context
            } else {
                throw IllegalStateException("LocalLifecycleOwner is not MainActivity or Fragment")
            }
        }

    }
    if (handleBackHandler) {
        activity.onBackInvokedDispatcher.registerOnBackInvokedCallback(
            priority,
            backInvokedCallback
        )
    }

    LaunchedEffect(handleBackHandler) {
        if (!handleBackHandler) {
            activity.onBackInvokedDispatcher.unregisterOnBackInvokedCallback(backInvokedCallback)
        }
    }


    DisposableEffect(activity.lifecycle, activity.onBackInvokedDispatcher) {
        onDispose {
            activity.onBackInvokedDispatcher.unregisterOnBackInvokedCallback(backInvokedCallback)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BackPressJetpackTheme {
        MainScreen()
    }
}

@Composable
fun BoxView(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = { }
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)
            }
        )
    ) {
        Box(
            modifier = Modifier.size(64.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text
            )
        }
    }
}

@Preview
@Composable
fun PreviewBoxView() {
    BackPressJetpackTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BoxView("1")
            BoxView(text = "2", isSelected = true)
        }
    }
}