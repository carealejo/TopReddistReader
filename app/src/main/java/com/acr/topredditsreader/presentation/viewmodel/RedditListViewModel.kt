package com.acr.topredditsreader.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acr.topredditsreader.R
import com.acr.topredditsreader.core.platform.BaseViewModel
import com.acr.topredditsreader.domain.datainformation.GetRedditDataUseCase
import com.acr.topredditsreader.domain.datainformation.InitRedditDataBaseUseCase
import com.acr.topredditsreader.domain.model.RedditDataRoot
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RedditListViewModel @Inject constructor(
    private val getRedditDataUseCase: GetRedditDataUseCase,
    private val initRedditDataBaseUseCase: InitRedditDataBaseUseCase
) : BaseViewModel() {

    private var redditDataInternal = MutableLiveData<RedditDataRoot>()
    var redditData: LiveData<RedditDataRoot> = redditDataInternal

    private var redditMoreDataInternal = MutableLiveData<RedditDataRoot>()
    var redditMoreData: LiveData<RedditDataRoot> = redditMoreDataInternal

    private var onErrorRedditDataInternal = MutableLiveData<String>()
    var onErrorRedditData: LiveData<String> = onErrorRedditDataInternal

    private var onLocalErrorRedditDataInternal = MutableLiveData<Int>()
    var onLocalErrorRedditData: LiveData<Int> = onLocalErrorRedditDataInternal

    fun initRedditDataBase() {
        disposable.add(initRedditDataBaseUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {})
    }

    fun getRedditData() {
        disposable.add(getRedditDataUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                if (it.success) {
                    redditDataInternal.value = it.value
                } else {
                    onError(it.error)
                }
            })
    }

    fun getMoreRedditData() {
        disposable.add(getRedditDataUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                if (it.success) {
                    redditMoreDataInternal.value = it.value
                } else {
                    onError(it.error)
                }
            })
    }

    private fun onError(errorInfo: String?) {
        errorInfo?.let {
            onErrorRedditDataInternal.value = it
        } ?: run {
            onLocalErrorRedditDataInternal.value = R.string.general_error
        }
    }
}