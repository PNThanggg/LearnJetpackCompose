package com.pnt.compose_neumorphism

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnt.compose_neumorphism.neumorphic.LightSource
import com.pnt.compose_neumorphism.neumorphic.NeuAttrs
import com.pnt.compose_neumorphism.neumorphic.neu
import com.pnt.compose_neumorphism.neumorphic.shape.CornerShape
import com.pnt.compose_neumorphism.neumorphic.shape.Flat
import com.pnt.compose_neumorphism.neumorphic.shape.NeuShape
import com.pnt.compose_neumorphism.neumorphic.shape.Oval
import com.pnt.compose_neumorphism.neumorphic.shape.Pressed
import com.pnt.compose_neumorphism.neumorphic.shape.RoundedCorner
import com.pnt.compose_neumorphism.ui.theme.AppColors
import com.pnt.compose_neumorphism.ui.theme.AppTextStyle
import com.pnt.compose_neumorphism.ui.theme.ComposeNeumorphismTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember {
                mutableStateOf(true)
            }

            ComposeNeumorphismTheme(darkTheme = isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        TitleWithThemeToggle(
                            title = getString(R.string.app_name),
                            isDarkTheme = isDarkTheme
                        ) {
                            isDarkTheme = !isDarkTheme
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            InputBoxWithCardWrapper()
                            PlainInputBox()
                            CheckBoxAndRadioButtons()
                            PressedSlider()
                            FlatSlider()
                            PressedButton()
                            FlatButton()
                            CircleActionButton()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun TitleWithThemeToggle(title: String, isDarkTheme: Boolean, onThemeToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = defaultWidgetPadding),
            text = title,
            style = AppTextStyle.bodyLarge, maxLines = 1
        )
        ImageButton(
            modifier = Modifier.padding(defaultWidgetPadding),
            drawableResId = if (isDarkTheme) R.drawable.ic_baseline_light_mode
            else R.drawable.ic_baseline_dark_mode_24,
            contentDescription = "Toggle theme",
            onClick = onThemeToggle
        )
    }
}

@Composable
fun DefaultSpacer() = Spacer(modifier = Modifier.size(8.dp))

val defaultWidgetPadding = 16.dp
val defaultElevation = 6.dp
val defaultCornerShape: CornerShape = RoundedCorner(12.dp)

@Composable
fun defaultPressedNetAttrs() = NeuAttrs(
    lightShadowColor = AppColors.lightShadow(),
    darkShadowColor = AppColors.darkShadow(),
    shadowElevation = defaultElevation,
    lightSource = LightSource.LEFT_TOP,
    shape = Pressed(defaultCornerShape),
)

@Composable
fun defaultFlatNeuAttrs() = NeuAttrs(
    lightShadowColor = AppColors.lightShadow(),
    darkShadowColor = AppColors.darkShadow(),
    shadowElevation = defaultElevation,
    lightSource = LightSource.LEFT_TOP,
    shape = Flat(defaultCornerShape)
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InputBoxWithCardWrapper() {
    var searchText by remember {
        mutableStateOf("")
    }
    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
            .neu(defaultPressedNetAttrs()),
    ) {
        TextField(
            value = searchText, onValueChange = { searchText = it },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
            ),
            textStyle = AppTextStyle.bodyLarge,
            placeholder = { Text(text = "Search", style = AppTextStyle.bodyLarge, maxLines = 1) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PlainInputBox() {
    var searchText by remember {
        mutableStateOf("")
    }
    TextField(
        value = searchText, onValueChange = { searchText = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
            .neu(defaultPressedNetAttrs()),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.Transparent,
        ),
        textStyle = AppTextStyle.bodyLarge,
        placeholder = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Search", style = AppTextStyle.bodyLarge)
            }
        },
    )
}

@Preview
@Composable
fun CheckBoxAndRadioButtons() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
    ) {
        var pressedStyleCheckBoxState by remember {
            mutableStateOf(true)
        }
        Checkbox(
            modifier = Modifier
                .neu(defaultPressedNetAttrs()),
            checked = pressedStyleCheckBoxState,
            onCheckedChange = { pressedStyleCheckBoxState = it }
        )

        var flatStyleCheckBoxState by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .neu(defaultFlatNeuAttrs())
        ) {
            Checkbox(
                checked = flatStyleCheckBoxState,
                onCheckedChange = { flatStyleCheckBoxState = it })
        }

        var pressedRadioButtonState by remember {
            mutableStateOf(true)
        }
        RadioButton(
            modifier = Modifier
                .neu(defaultPressedNetAttrs()),
            selected = pressedRadioButtonState,
            onClick = { pressedRadioButtonState = !pressedRadioButtonState })

        var flatRadioButtonState by remember {
            mutableStateOf(false)
        }

        Card(
            modifier = Modifier
                .neu(defaultFlatNeuAttrs())
        ) {
            RadioButton(
                selected = flatRadioButtonState,
                onClick = { flatRadioButtonState = !flatRadioButtonState })
        }
    }
}

@Preview
@Composable
fun PressedSlider() {
    var sliderValue by remember {
        mutableFloatStateOf(defaultElevation.value)
    }
    Slider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
            .neu(defaultPressedNetAttrs()),
        value = sliderValue, onValueChange = { sliderValue = it }, valueRange = 0f..12f
    )
}


@Preview
@Composable
fun FlatSlider() {
    var sliderValue by remember {
        mutableFloatStateOf(defaultElevation.value)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
            .neu(defaultFlatNeuAttrs())
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            Slider(
                value = sliderValue, onValueChange = { sliderValue = it }, valueRange = 0f..12f
            )
        }
    }

}


@Preview
@Composable
fun PressedButton() {
    Button(
        onClick = { }, modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
            .neu(defaultPressedNetAttrs()),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = null

    ) {
        Text(text = "Button", style = AppTextStyle.bodyLarge)
    }
}

@Preview
@Composable
fun FlatButton() {
    Button(
        onClick = { /*TODO*/ }, modifier = Modifier
            .defaultMinSize(minHeight = 80.dp)
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
            .neu(defaultFlatNeuAttrs()),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = "Button", style = AppTextStyle.bodyLarge
        )
    }
}

@Preview
@Composable
fun CircleActionButton() {
    val imageSize = 48.dp
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultWidgetPadding)
    ) {
        Image(
            modifier = Modifier
                .size(imageSize)
                .neu(
                    lightShadowColor = AppColors.lightShadow(),
                    darkShadowColor = AppColors.darkShadow(),
                    shadowElevation = defaultElevation,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Pressed(Oval),
                ),
            painter = painterResource(id = R.drawable.ic_baseline_emoji_events_24),
            contentDescription = "Pressed image 1",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        DefaultSpacer()
        Image(
            modifier = Modifier
                .size(imageSize)
                .neu(
                    lightShadowColor = AppColors.lightShadow(),
                    darkShadowColor = AppColors.darkShadow(),
                    shadowElevation = defaultElevation,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Pressed(Oval),
                ),
            painter = painterResource(id = R.drawable.ic_baseline_thumb_up_24),
            contentDescription = "Pressed image 2",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        DefaultSpacer()

        Card(
            modifier = Modifier
                .size(imageSize)
                .neu(
                    lightShadowColor = AppColors.lightShadow(),
                    darkShadowColor = AppColors.darkShadow(),
                    shadowElevation = defaultElevation,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Flat(Oval),
                ),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_emoji_emotions_24),
                contentDescription = "Flat image 1",
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
        DefaultSpacer()
        Card(
            modifier = Modifier
                .size(imageSize)
                .neu(
                    lightShadowColor = AppColors.lightShadow(),
                    darkShadowColor = AppColors.darkShadow(),
                    shadowElevation = defaultElevation,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Flat(Oval),
                ),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_anchor_24),
                contentDescription = "Flat image 2",
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun ImageButton(
    modifier: Modifier,
    @DrawableRes drawableResId: Int,
    contentDescription: String = "",
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .size(48.dp)
            .neu(
                lightShadowColor = AppColors.lightShadow(),
                darkShadowColor = AppColors.darkShadow(),
                shadowElevation = defaultElevation,
                lightSource = LightSource.LEFT_TOP,
                shape = Flat(Oval),
            ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Image(
            modifier = Modifier.clickable(true, onClick = onClick),
            painter = painterResource(id = drawableResId),
            contentDescription = contentDescription,
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun PressedSwitch() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Switch(
        checked = isChecked, onCheckedChange = { isChecked = !isChecked },
        modifier = Modifier.neu(
            NeuAttrs(
                lightShadowColor = AppColors.lightShadow(),
                darkShadowColor = AppColors.darkShadow(),
                shadowElevation = defaultElevation,
                lightSource = LightSource.LEFT_TOP,
                shape = Pressed(RoundedCorner(0.dp)),
            )
        )
    )
}

@Composable
fun PressedExample() {
    Card(
        modifier = Modifier
            .size(128.dp)
            .neu(
                lightShadowColor = AppColors.lightShadow(),
                darkShadowColor = AppColors.darkShadow(),
                shadowElevation = defaultElevation,
                lightSource = LightSource.LEFT_TOP,
                shape = Pressed(RoundedCorner(24.dp)),
            ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(24.dp),
    ) {}
}

@Composable
fun ElevatedExample(lightSource: LightSource, shape: NeuShape) {
    Card(
        modifier = Modifier
            .size(96.dp)
            .neu(
                NeuAttrs(
                    lightShadowColor = AppColors.lightShadow(),
                    darkShadowColor = AppColors.darkShadow(),
                    shadowElevation = defaultElevation,
                    lightSource = lightSource,
                    shape = shape,
                )
            ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(24.dp),
    ) {}
}
