package com.kuvalin.brainstorm.di

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.BrainStormMainViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.GamesViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.MainMenuViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.MenuViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.ProfileViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.ShareStatisticsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.StatisticsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.WarViewModel
import com.sumin.vknewsclient.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Урок - https://stepik.org/lesson/926374/step/1?unit=932257
@Module
interface ViewModelModule {

    // ###################### Home
    // Main (навигация)
    @IntoMap
    @ViewModelKey(BrainStormMainViewModel::class)
    @Binds
    fun bindBrainStormMainViewModel(viewModel: BrainStormMainViewModel): ViewModel

    // War
    @IntoMap
    @ViewModelKey(WarViewModel::class)
    @Binds
    fun bindWarsViewModel(viewModel: WarViewModel): ViewModel



    // MainMenuScreen
    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    @Binds
    fun bindMainMenuViewModel(viewModel: MainMenuViewModel): ViewModel

    // MainMenuScreen
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    @Binds
    fun bindMenuViewModel(viewModel: MenuViewModel): ViewModel

    // ProfileScreen
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    // TopAppBar > ShareStatisticDialog
    @IntoMap
    @ViewModelKey(ShareStatisticsViewModel::class)
    @Binds
    fun bindShareStatisticsViewModel(viewModel: ShareStatisticsViewModel): ViewModel
    // ######################



    // ###################### Friends
    @IntoMap
    @ViewModelKey(FriendsViewModel::class)
    @Binds
    fun bindFriendsViewModel(viewModel: FriendsViewModel): ViewModel
    // ######################



    // ###################### Achievements
    // ######################



    // ###################### Statistics
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    @Binds
    fun bindStatisticsViewModel(viewModel: StatisticsViewModel): ViewModel
    // ######################



    // ###################### Games
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    @Binds
    fun bindGamesViewModel(viewModel: GamesViewModel): ViewModel
    // ######################









}
