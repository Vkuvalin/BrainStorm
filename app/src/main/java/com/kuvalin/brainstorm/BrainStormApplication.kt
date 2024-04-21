package com.kuvalin.brainstorm

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kuvalin.brainstorm.di.ApplicationComponent
import com.kuvalin.brainstorm.di.DaggerApplicationComponent

class BrainStormApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as BrainStormApplication).component
}