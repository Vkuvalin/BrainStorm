package com.kuvalin.brainstorm.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
object FirebaseModule {

    @Provides
    @ApplicationScope
    fun provideFirebase(): Firebase {
        return Firebase
    }

}