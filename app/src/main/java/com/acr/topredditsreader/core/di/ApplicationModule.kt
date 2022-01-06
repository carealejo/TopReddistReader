package com.acr.topredditsreader.core.di

import android.content.Context
import com.acr.topredditsreader.TopReddistReaderApplication
import com.acr.topredditsreader.data.repository.GetRedditDataRepoImpl
import com.acr.topredditsreader.data.service.GetRedditDataService
import com.acr.topredditsreader.domain.datainformation.GetRedditDataUseCase
import com.acr.topredditsreader.domain.repository.GetRedditDataRepo
import com.acr.topredditsreader.presentation.viewmodel.RedditListViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: TopReddistReaderApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://www.reddit.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Service
    @Provides
    @Singleton
    fun provideGetDataService(retrofit: Retrofit) = GetRedditDataService(retrofit)

    // Repository
    @Provides
    @Singleton
    fun provideGetDataRepo(getRedditDataService: GetRedditDataService): GetRedditDataRepo = GetRedditDataRepoImpl(getRedditDataService)

    // UseCase
    @Provides
    @Singleton
    fun provideGetDataInformationUseCase(getRedditDataRepo: GetRedditDataRepo) = GetRedditDataUseCase(getRedditDataRepo)

    // ViewModel
    @Provides
    fun provideHomeViewModel(getRedditDataUseCase: GetRedditDataUseCase) = RedditListViewModel(getRedditDataUseCase)
}
