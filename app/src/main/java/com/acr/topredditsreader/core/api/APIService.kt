package com.acr.topredditsreader.core.api

import com.acr.topredditsreader.domain.model.RedditDataRoot
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("/top.json") fun getRedditData(): Call<RedditDataRoot>
}