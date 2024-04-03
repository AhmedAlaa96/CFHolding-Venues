package com.ahmed.cfholding_venues.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.di.MainDispatcher
import com.ahmed.cfholding_venues.domain.usecases.home.IHomeUseCase
import com.ahmed.cfholding_venues.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val mUseCase: IHomeUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : BaseViewModel(handle, mUseCase) {
    private var venuesResponseStatus: Status<ArrayList<Venue>>? = null


    private val _venuesResponseMutableSharedFlow = MutableSharedFlow<Status<ArrayList<Venue>>>()
    val venuesResponseMutableSharedFlow = _venuesResponseMutableSharedFlow.asSharedFlow()


    fun getVenuesListResponse(venuesRequest: VenuesRequest) {
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(mainDispatcher + handler) {
            if (venuesResponseStatus != null && venuesResponseStatus?.isIdle() != true) {
                setMoviesResponseStatus(venuesResponseStatus!!)
            } else {
                callGetMoviesList(ProgressTypes.MAIN_PROGRESS, venuesRequest)
            }
        }
    }

    private suspend fun callGetMoviesList(progressType: ProgressTypes, venuesRequest: VenuesRequest) {
        onGetMoviesSubscribe(progressType)
        mUseCase.getVenuesResponse(venuesRequest)
            .onStart {
                showProgress(true, progressType)
            }.onCompletion {
                showProgress(false, progressType)
            }.catch {
                setMoviesResponseStatus(Status.Error(error = it.message))
                showProgress(false, progressType)
            }
            .collect {
                setMoviesResponseStatus(it)
            }
    }

    private fun onGetMoviesSubscribe(progressType: ProgressTypes) {
        showProgress(true, progressType)
        shouldShowError(false)
    }

    private suspend fun setMoviesResponseStatus(moviesResponseStatus: Status<ArrayList<Venue>>) {
        if (!moviesResponseStatus.isSuccess()) {
            val movies = this.venuesResponseStatus?.data
            this.venuesResponseStatus = Status.Success(movies)
        } else {
            this.venuesResponseStatus = moviesResponseStatus
        }

        _venuesResponseMutableSharedFlow.emit(moviesResponseStatus)
    }

}