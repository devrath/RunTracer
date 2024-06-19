package com.istudio.core.presentation.designsystem.dimen.screens

import androidx.compose.ui.unit.Dp
import com.istudio.core.presentation.designsystem.dimen.RunTracerDimens

open class IntroScreenDimen {
    open val logoBrandNameSpacing: Dp = RunTracerDimens.SpacingScale.dp12
}

class IntroScreenDimenMedium : IntroScreenDimen() {
    override val logoBrandNameSpacing: Dp = RunTracerDimens.SpacingScale.dp14
}

class IntroScreenDimenExpanded : IntroScreenDimen() {
    override val logoBrandNameSpacing: Dp = RunTracerDimens.SpacingScale.dp16
}