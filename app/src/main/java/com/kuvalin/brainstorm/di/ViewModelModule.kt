package com.kuvalin.brainstorm.di

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.GamesViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.MainMenuViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.MainViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.StatisticsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.WarViewModel
import com.sumin.vknewsclient.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Урок - https://stepik.org/lesson/926374/step/1?unit=932257
@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    @Binds
    fun bindMainMenuViewModel(viewModel: MainMenuViewModel): ViewModel


    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    @Binds
    fun bindGamesViewModel(viewModel: GamesViewModel): ViewModel


    @IntoMap
    @ViewModelKey(WarViewModel::class)
    @Binds
    fun bindWarsViewModel(viewModel: WarViewModel): ViewModel


    @IntoMap
    @ViewModelKey(FriendsViewModel::class)
    @Binds
    fun bindFriendsViewModel(viewModel: FriendsViewModel): ViewModel


    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    @Binds
    fun bindStatisticsViewModel(viewModel: StatisticsViewModel): ViewModel


    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}