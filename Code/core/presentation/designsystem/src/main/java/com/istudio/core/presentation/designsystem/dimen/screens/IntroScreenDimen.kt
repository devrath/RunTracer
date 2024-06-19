package com.istudio.core.presentation.designsystem.dimen.screens

import androidx.compose.ui.unit.Dp
import com.istudio.core.presentation.designsystem.dimen.RunTracerDimens

open class IntroScreenDimen {
    open val sample: Dp = RunTracerDimens.SpacingScale.dp10
}

class IntroScreenDimenMedium : IntroScreenDimen() {
    override val sample: Dp = RunTracerDimens.SpacingScale.dp15
}

class IntroScreenDimenExpanded : IntroScreenDimen() {
    override val sample: Dp = RunTracerDimens.SpacingScale.dp20
}