package com.antonystudio.facts.api

sealed class NetworkState {
    object NetworkNotAvailable : NetworkState()
    object LoadingData : NetworkState()
    object ErrorResponse : NetworkState()
    class SuccessResponse<T>(val data: T?) : NetworkState()
}