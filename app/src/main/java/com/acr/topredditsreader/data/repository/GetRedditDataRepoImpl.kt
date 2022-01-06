package com.acr.topredditsreader.data.repository

import com.acr.topredditsreader.data.service.GetRedditDataService
import com.acr.topredditsreader.domain.model.RedditDataRoot
import com.acr.topredditsreader.domain.model.Response
import com.acr.topredditsreader.domain.repository.GetRedditDataRepo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import javax.inject.Inject

class GetRedditDataRepoImpl @Inject constructor(private val getRedditDataService: GetRedditDataService) : GetRedditDataRepo {

    override fun getData() = Observable.create(object: ObservableOnSubscribe<Response<RedditDataRoot>> {
        override fun subscribe(emitter: ObservableEmitter<Response<RedditDataRoot>>) {
            emitter.onNext(getRedditDataService.getData())
            emitter.onComplete()
        }
    })
}