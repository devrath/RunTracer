package com.istudio.core.presentation.designsystem.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * This file contains annotations for various UI previews. These annotations help in
 * testing the UI across different themes (light and dark) and device sizes (compact, medium, expanded).
 * The goal is to ensure a consistent and responsive UI across a wide range of configurations.
 */

//<----------------------- Constants ---------------------------------------->

// Constants for background colors
private const val LightBackgroundColor = 0xFFFFFFFF
private const val DarkBackgroundColor = 0xFF0D0D0D

// Constants for device specifications
private const val CompactDeviceSpec = "spec:width=360dp,height=1024dp,dpi=480"
private const val MediumDeviceSpec = "spec:width=600dp,height=1024dp,dpi=480"
private const val ExpandedDeviceSpec = "spec:width=840dp,height=1024dp,dpi=480"

// Constants for preview names
private const val LightPreviewName = "Light"
private const val DarkPreviewName = "Dark"
private const val CompactPreviewName = "Compact"
private const val CompactDarkPreviewName = "Compact - Dark"
private const val MediumPreviewName = "Medium"
private const val MediumDarkPreviewName = "Medium - Dark"
private const val ExpandedPreviewName = "Expanded"
private const val ExpandedDarkPreviewName = "Expanded - Dark"

//<----------------------- Constants ---------------------------------------->

//<----------------------- Combined Previews ------------------------------->

/**
 * Combines previews for compact, medium, and expanded device sizes.
 * Used to provide a comprehensive view of the UI across different screen sizes.
 */
@DeviceCompactPreviews
@DeviceMediumPreviews
@DeviceExpandedPreviews
annotation class WindowSizeClassPreviews

/**
 * Combines theme and window size class previews.
 * Provides a complete set of previews for light and dark themes across various screen sizes.
 */
@ThemePreviews
@WindowSizeClassPreviews
annotation class CompletePreviews
//<----------------------- Combined Previews -------------------------------->

//<----------------------- Theme Previews ----------------------------------->

/**
 * Defines previews for light and dark themes.
 * Useful for ensuring the UI looks good in both light and dark modes.
 */
@Preview(
    name = LightPreviewName,
    showBackground = true,
    backgroundColor = LightBackgroundColor
)
@Preview(
    name = DarkPreviewName,
    showBackground = true,
    backgroundColor = DarkBackgroundColor,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class ThemePreviews
//<----------------------- Theme Previews ----------------------------------->

//<----------------------- Device Compact Previews -------------------------->

/**
 * Defines previews for compact device sizes.
 * Ensures the UI is responsive and visually appealing on smaller screens (e.g., small phones).
 */
@Preview(
    name = CompactPreviewName,
    showBackground = true,
    backgroundColor = LightBackgroundColor,
    device = CompactDeviceSpec
)
@Preview(
    name = CompactDarkPreviewName,
    showBackground = true,
    backgroundColor = DarkBackgroundColor,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = CompactDeviceSpec
)
annotation class DeviceCompactPreviews
//<----------------------- Device Compact Previews -------------------------->

//<----------------------- Device Medium Previews -------------------------->

/**
 * Defines previews for medium device sizes.
 * Useful for testing the UI on medium-sized screens, such as tablets.
 */
@Preview(
    name = MediumPreviewName,
    showBackground = true,
    backgroundColor = LightBackgroundColor,
    device = MediumDeviceSpec
)
@Preview(
    name = MediumDarkPreviewName,
    showBackground = true,
    backgroundColor = DarkBackgroundColor,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = MediumDeviceSpec
)
annotation class DeviceMediumPreviews
//<----------------------- Device Medium Previews -------------------------->

//<----------------------- Device Expanded Previews ------------------------->

/**
 * Defines previews for expanded device sizes.
 * Ensures the UI scales well on larger screens, such as large tablets and desktops.
 */
@Preview(
    name = ExpandedPreviewName,
    showBackground = true,
    backgroundColor = LightBackgroundColor,
    device = ExpandedDeviceSpec
)
@Preview(
    name = ExpandedDarkPreviewName,
    showBackground = true,
    backgroundColor = DarkBackgroundColor,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = ExpandedDeviceSpec
)
annotation class DeviceExpandedPreviews
//<----------------------- Device Expanded Previews ----------------------->
