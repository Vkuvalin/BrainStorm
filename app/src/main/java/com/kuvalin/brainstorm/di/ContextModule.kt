package com.kuvalin.brainstorm.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
interface ContextModule {

    @Binds
    @ApplicationScope
    fun context(appInstance: Application): Context

}