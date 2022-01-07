package com.acr.topredditsreader.domain.datainformation

import com.acr.topredditsreader.domain.model.Response
import com.acr.topredditsreader.domain.repository.GetRedditDataRepo
import io.reactivex.Observable
import javax.inject.Inject

class InitRedditDataBaseUseCase @Inject constructor(private val repository: GetRedditDataRepo) {

    operator fun invoke(): Observable<Response<Long>> {
        return repository.initDataBase()
    }
}