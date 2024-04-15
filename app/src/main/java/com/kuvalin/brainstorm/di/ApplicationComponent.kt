package com.kuvalin.brainstorm.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [DataModule::class, ContextModule::class]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create (
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}