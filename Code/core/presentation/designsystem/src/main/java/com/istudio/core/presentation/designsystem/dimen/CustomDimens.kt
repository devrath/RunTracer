package com.istudio.core.presentation.designsystem.dimen

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.staticCompositionLocalOf
import com.istudio.core.presentation.designsystem.dimen.screens.IntroScreenDimen
import com.istudio.core.presentation.designsystem.dimen.screens.IntroScreenDimenMedium

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
