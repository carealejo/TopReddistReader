package com.acr.topredditsreader.data.service

import android.util.Log
import com.acr.topredditsreader.core.api.APIService
import com.acr.topredditsreader.domain.model.RedditDataRoot
import com.acr.topredditsreader.domain.model.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class GetRedditDataService @Inject constructor(private val retrofit: Retrofit) {

    fun getData(): Response<RedditDataRoot> {
        try {
            retrofit.create(APIService::class.java).getRedditData().execute().also {
                return if (it.isSuccessful && it.body() is RedditDataRoot) {
                    Response(it.body())
                } else {
                    Log.e(javaClass.name, it.errorBody().toString())
                    Response(success = false, error = it.errorBody().toString())
                }
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString())
            return Response(success = false, error = e.toString())
        }
    }
}