package com.acr.topredditsreader.domain.repository

import com.acr.topredditsreader.domain.model.RedditData
import com.acr.topredditsreader.domain.model.RedditDataRoot
import com.acr.topredditsreader.domain.model.Response
import io.reactivex.Observable

interface GetRedditDataRepo {

    fun getData(): Observable<Response<RedditDataRoot>>
}