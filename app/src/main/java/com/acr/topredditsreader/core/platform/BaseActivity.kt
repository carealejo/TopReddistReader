package com.acr.topredditsreader.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acr.topredditsreader.TopReddistReaderApplication
import com.acr.topredditsreader.core.di.ApplicationComponent

/*
* Base Activity to help general behaviors on the activities
*/
abstract class BaseActivity: AppCompatActivity() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as TopReddistReaderApplication).appComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
    }
}