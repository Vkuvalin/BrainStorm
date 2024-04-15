package com.kuvalin.brainstorm.di

import android.content.Context
import com.kuvalin.brainstorm.data.database.AppDatabase
import com.kuvalin.brainstorm.data.database.UserDataDao
import com.kuvalin.brainstorm.data.repository.BrainStormRepositoryImpl
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindBrainStormRepository(impl: BrainStormRepositoryImpl): BrainStormRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideUserDataDao(
            context: Context
        ): UserDataDao {
            return AppDatabase.getInstance(context).userDataDao()
        }
    }

}