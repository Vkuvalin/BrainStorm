package com.kuvalin.brainstorm.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.data.database.AppDatabase
import com.kuvalin.brainstorm.data.database.UserDataDao
import com.kuvalin.brainstorm.data.firebase.ApiService
import com.kuvalin.brainstorm.data.firebase.FirebaseApiService
import com.kuvalin.brainstorm.data.mapper.BrainStormMapper
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
        fun provideApiService(
            fireBase: Firebase,
            mapper: BrainStormMapper
        ): ApiService {
            return FirebaseApiService(fireBase, mapper)
        }

        @Provides
        @ApplicationScope
        fun provideUserDataDao(
            context: Context
        ): UserDataDao {
            return AppDatabase.getInstance(context).userDataDao()
        }
    }

}