package com.ahmed.cfholding_venues.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.di.MainDispatcher
import com.ahmed.cfholding_venues.domain.usecases.profile.IProfileUseCase
import com.ahmed.cfholding_venues.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mUseCase: IProfileUseCase,
    handle: SavedStateHandle,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : BaseViewModel(handle, mUseCase) {
    private val _userDataSharedFlow = MutableSharedFlow<Status<User>>()
    val userDataSharedFlow = _userDataSharedFlow.asSharedFlow()


    init {
        getUserData()
    }

    fun getUserData(){
        viewModelScope.launch(mainDispatcher) {
            mUseCase.getUserData()
                .collect{
                    _userDataSharedFlow.emit(it)
                }
        }
    }

}