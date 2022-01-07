package com.acr.topredditsreader.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acr.topredditsreader.R
import com.acr.topredditsreader.core.platform.BaseViewModel
import com.acr.topredditsreader.domain.datainformation.GetRedditDataUseCase
import com.acr.topredditsreader.domain.datainformation.InitRedditDataBaseUseCase
import com.acr.topredditsreader.domain.model.RedditChild
import com.acr.topredditsreader.domain.model.RedditChildData
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

    private var openRedditDetailsInternal = MutableLiveData<RedditChildData>()
    var openRedditDetails: LiveData<RedditChildData> = openRedditDetailsInternal

    companion object {
        private const val FORMAT_PNG = ".png"
        private const val FORMAT_JPG = ".jpg"
    }

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

    fun checkRedditDetails(redditChildData: RedditChildData) {
        redditChildData.url_overridden_by_dest?.let { imageUrl ->
            val extention =  imageUrl.substring(imageUrl.lastIndexOf("."));
            if (extention.equals(FORMAT_PNG) || extention.equals(FORMAT_JPG)) {
                openRedditDetailsInternal.value = redditChildData
            } else {
                onError(localError = R.string.image_not_supported)
            }
        } ?: run {
            onError()
        }
    }

    private fun onError(errorInfo: String? = null, localError: Int = R.string.general_error) {
        errorInfo?.let {
            onErrorRedditDataInternal.value = it
        } ?: run {
            onLocalErrorRedditDataInternal.value = localError
        }
    }
}