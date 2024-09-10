package com.kuvalin.brainstorm.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ContextModule::class,
        ViewModelModule::class,
        FirebaseModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create (
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}