package com.acr.topredditsreader.domain.datainformation

import com.acr.topredditsreader.domain.model.RedditDataRoot
import com.acr.topredditsreader.domain.model.Response
import com.acr.topredditsreader.domain.repository.GetRedditDataRepo
import io.reactivex.Observable
import javax.inject.Inject

class GetRedditDataUseCase @Inject constructor(private val repository: GetRedditDataRepo) {

    operator fun invoke(): Observable<Response<RedditDataRoot>> {
        return repository.getData()
    }
}