package com.acr.topredditsreader.core.di

import com.acr.topredditsreader.TopReddistReaderApplication
import com.acr.topredditsreader.presentation.activity.RedditListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [ApplicationModule::class]
)

interface ApplicationComponent {
    fun inject(application: TopReddistReaderApplication)
    fun inject(redditListActivity: RedditListActivity)
}
