package com.kuvalin.brainstorm.di

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.achievement.AchievementsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.friends.FriendsContentViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.friends.FriendsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.friends.MessageContentViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.friends.RequestContentViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameMainScreenViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameScreenViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameSettingsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.main.BrainStormMainViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.main.MainMenuViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.main.MenuViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.main.ProfileViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.main.ShareStatisticsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.main.WarViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.statistics.GamesStatisticsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.statistics.FriendsStatisticsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.statistics.WarsStatisticsViewModel
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


    @IntoMap
    @ViewModelKey(FriendsContentViewModel::class)
    @Binds
    fun bindFriendsContentViewModel(viewModel: FriendsContentViewModel): ViewModel


    @IntoMap
    @ViewModelKey(MessageContentViewModel::class)
    @Binds
    fun bindMessageContentViewModel(viewModel: MessageContentViewModel): ViewModel


    @IntoMap
    @ViewModelKey(RequestContentViewModel::class)
    @Binds
    fun bindRequestContentViewModel(viewModel: RequestContentViewModel): ViewModel
    // ######################



    // ###################### Achievements
    @IntoMap
    @ViewModelKey(AchievementsViewModel::class)
    @Binds
    fun bindAchievementsViewModel(viewModel: AchievementsViewModel): ViewModel
    // ######################



    // ###################### Statistics
    @IntoMap
    @ViewModelKey(FriendsStatisticsViewModel::class)
    @Binds
    fun bindStatisticsViewModel(viewModel: FriendsStatisticsViewModel): ViewModel


    @IntoMap
    @ViewModelKey(WarsStatisticsViewModel::class)
    @Binds
    fun bindWarsStatisticsViewModel(viewModel: WarsStatisticsViewModel): ViewModel


    @IntoMap
    @ViewModelKey(GamesStatisticsViewModel::class)
    @Binds
    fun bindGamesStatisticsViewModel(viewModel: GamesStatisticsViewModel): ViewModel
    // ######################



    // ###################### Games
    @IntoMap
    @ViewModelKey(GameMainScreenViewModel::class)
    @Binds
    fun bindGameMainScreenViewModel(viewModel: GameMainScreenViewModel): ViewModel

    @IntoMap
    @ViewModelKey(GameSettingsViewModel::class)
    @Binds
    fun bindGameSettingsViewModel(viewModel: GameSettingsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(GameScreenViewModel::class)
    @Binds
    fun bindGameScreenViewModel(viewModel: GameScreenViewModel): ViewModel
    // ######################



}
