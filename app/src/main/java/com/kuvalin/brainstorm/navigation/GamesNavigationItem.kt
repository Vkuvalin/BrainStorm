package com.kuvalin.brainstorm.navigation

import com.kuvalin.brainstorm.navigation.staticsClasses.Screen
import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.GamesScreen

sealed class GamesNavigationItem (
    val screen: Screen,
    val sectionName: String
){

    object Operations: GamesNavigationItem (
        screen = GamesScreen.Operations,
        sectionName = "Operations"
    )
    object FlickMaster: GamesNavigationItem (
        screen = GamesScreen.FlickMaster,
        sectionName = "FlickMaster"
    )
    object Simplicity: GamesNavigationItem (
        screen = GamesScreen.Simplicity,
        sectionName = "Simplicity"
    )
    object ReverseRPS: GamesNavigationItem (
        screen = GamesScreen.ReverseRPS,
        sectionName = "ReverseRPS"
    )
    object TouchTheNumber: GamesNavigationItem (
        screen = GamesScreen.TouchTheNumber,
        sectionName = "TouchTheNumber"
    )
    object FollowTheLeader: GamesNavigationItem (
        screen = GamesScreen.FollowTheLeader,
        sectionName = "FollowTheLeader"
    )
    object AdditionAddiction: GamesNavigationItem (
        screen = GamesScreen.AdditionAddiction,
        sectionName = "AdditionAddiction"
    )
    object Matching: GamesNavigationItem (
        screen = GamesScreen.Matching,
        sectionName = "Matching"
    )
    object ColorOfDeception: GamesNavigationItem (
        screen = GamesScreen.ColorOfDeception,
        sectionName = "ColorOfDeception"
    )
    object Concentration: GamesNavigationItem (
        screen = GamesScreen.Concentration,
        sectionName = "Concentration"
    )
    object HighOrLow: GamesNavigationItem (
        screen = GamesScreen.HighOrLow,
        sectionName = "HighOrLow"
    )
    object BirdWatching: GamesNavigationItem (
        screen = GamesScreen.BirdWatching,
        sectionName = "BirdWatching"
    )
    object AdditionLink: GamesNavigationItem (
        screen = GamesScreen.AdditionLink,
        sectionName = "AdditionLink"
    )
    object Reflection: GamesNavigationItem (
        screen = GamesScreen.Reflection,
        sectionName = "Reflection"
    )
    object UnfollowTheLeader: GamesNavigationItem (
        screen = GamesScreen.UnfollowTheLeader,
        sectionName = "UnfollowTheLeader"
    )



}