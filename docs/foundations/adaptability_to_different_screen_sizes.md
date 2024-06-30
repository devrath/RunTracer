# `Overview`
* Various screen displays in Android are becoming more and more important.
* In the earlier days handling the vast variety of devices with different sizes in Android was very challenging.
* Now in Android, the area is expanded to more things such as `TV`, `FOLDABLES`, `DESKTOP` and any device imaginable
* Also the device must adapt and be responsive to things like `ORIENTATION`, `SIZES`, and other form factors.

# `How the Screens are Categorized`
The window size class categorizes the types as
* `Compact`
* `Medium`
* `Expanded`

<div align="center">

![0_TfCECrHpqL_6D4vK](https://github.com/devrath/ComposeAlchemy/assets/1456191/3720f2fc-39c9-4701-8cb9-8d67c075ef24)

</div>

* `Width` and `Height` are calculated separately.
* At any given time there are two types of device sizes, We refer to them as `portrait` and `landscape`.

# `Importance of width`
* At any given time we have the ability of vertical-scrolling.
* The availability of width is more prominent than the height.
* So, the available width is more important than the height.

# `Implementation`

#### `Step-1`:`Add the gradle lines`
```gradle
implementation(platform("androidx.compose:compose-bom:2023.08.00"))
implementation("androidx.compose.material3:material3-window-size-class")
```
#### `Step-2`:`Modifiy the set content block`
The variable needs to be passed in composable so that it can be used throughout the application.
```kotlin
setContent {
   MyTheme {
       val windowSize = calculateWindowSizeClass(this)
       // Passthis window-size to your composables
       OurComposable(windowSize)
   }
}     
```
# `Using the custom dimension for each screen that adapt to screen sizes`

**Define the dimensions**
```kotlin
@Immutable
open class RunTracerDimens {
    open val margin: Dp = SpacingScale.dp0
    object SpacingScale {
        val dp0 = 0.dp
        val dp1 = 1.dp
        val dp2 = 2.dp
        // ---> More as needed
    }
}

class RunTracerDimensMedium : RunTracerDimens() {
    override val margin: Dp = SpacingScale.dp1
}

class RunTracerDimensExpanded : RunTracerDimens() {
    override val margin: Dp = SpacingScale.dp2
}
``` 
**Define the screen-wise dimensions for adapting screens**
```kotlin
open class IntroScreenDimen {
    open val logoBrandNameSpacing: Dp = RunTracerDimens.SpacingScale.dp12
}

class IntroScreenDimenMedium : IntroScreenDimen() {
    override val logoBrandNameSpacing: Dp = RunTracerDimens.SpacingScale.dp14
}

class IntroScreenDimenExpanded : IntroScreenDimen() {
    override val logoBrandNameSpacing: Dp = RunTracerDimens.SpacingScale.dp16
}
```
**Define a class that creates object of the above classes**
```kotlin
class CustomDimens(
    val app: RunTracerDimens,
    val introScreenDimen: IntroScreenDimen,
) {
    companion object {
        internal fun createLiskovDimenCompact(): CustomDimens {
            return CustomDimens(
                app = RunTracerDimens(),
                introScreenDimen = IntroScreenDimen()
            )
        }

        private fun createLiskovDimenMedium(): CustomDimens {
            return CustomDimens(
                app = RunTracerDimensMedium(),
                introScreenDimen = IntroScreenDimenMedium()
            )
        }

        private fun createLiskovDimenExpanded(): CustomDimens {
            return CustomDimens(
                app = RunTracerDimensExpanded(),
                introScreenDimen = IntroScreenDimenMedium()
            )
        }

        fun createCustomDimenByWindowSize(windowClassSize: WindowSizeClass?): CustomDimens {
            return when (windowClassSize?.widthSizeClass) {
                WindowWidthSizeClass.Compact -> createLiskovDimenCompact()
                WindowWidthSizeClass.Medium -> createLiskovDimenMedium()
                WindowWidthSizeClass.Expanded -> createLiskovDimenExpanded()
                else -> createLiskovDimenCompact()
            }
        }
    }
}

internal val LocalCustomDimens =
    staticCompositionLocalOf { CustomDimens.createLiskovDimenCompact() }
```
**Define the CustomDimens in your theme class as object to access it throughout the project**
```kotlin
@Composable
fun RunTracerTheme(
    // Flag to determine the dark/light theme
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    // Window Size class to determine the screen size
    windowClassSize: WindowSizeClass? = loadLocalWindowSizeClass(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    // Parent most top level composable
    content: @Composable () -> Unit
) {
   
}

object RunTracerTheme {
    val dimen: CustomDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomDimens.current
}
```
**Usage**
```kotlin
Spacer(modifier = Modifier.height(RunTracerTheme.dimen.introScreenDimen.logoBrandNameSpacing))
```