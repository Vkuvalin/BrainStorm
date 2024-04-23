package com.sumin.vknewsclient.di

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.GamesViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.MainMenuViewModel
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

}