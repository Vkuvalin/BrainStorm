package com.kuvalin.brainstorm.navigation.staticsClasses.animationStates

sealed class AnimationTopBarState() {

    object AnimationInitial : AnimationTopBarState()
    object AnimationTopBarWar : AnimationTopBarState()
    object AnimationTopBarChat : AnimationTopBarState()
    object AnimationTopBarGames : AnimationTopBarState()

}