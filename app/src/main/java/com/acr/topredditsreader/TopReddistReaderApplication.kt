package com.acr.topredditsreader

import android.app.Application
import com.acr.topredditsreader.core.di.ApplicationComponent
import com.acr.topredditsreader.core.di.ApplicationModule
import com.acr.topredditsreader.core.di.DaggerApplicationComponent

class TopReddistReaderApplication: Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}