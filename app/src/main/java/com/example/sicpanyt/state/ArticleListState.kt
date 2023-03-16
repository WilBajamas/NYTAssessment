package com.example.sicpanyt.state

sealed class State<out R> {
    class Loaded<out T>(val data: T) : State< T>()
    class LoadedFromDataBase<out T>(val data: T) : State< T>()
    class NextPageLoaded<out T>(val data: T) : State< T>()
    object NextPageLoading: State<Nothing>()
    object Loading : State<Nothing>()
    object LoadingFailed : State<Nothing>()
    object NextPageLoadingFailed : State<Nothing>()

}