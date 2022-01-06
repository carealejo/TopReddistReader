package com.acr.topredditsreader.domain.model

data class Response<out T>(val value: T? = null, val success: Boolean = true, val error: String? = null)