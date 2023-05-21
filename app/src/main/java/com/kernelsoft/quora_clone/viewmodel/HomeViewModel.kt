package com.kernelsoft.quora_clone.viewmodel

import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class HomeViewModel : ViewModel() {
    val sheetState: MutableState<ModalBottomSheetState> =  mutableStateOf(ModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = SwipeableDefaults.AnimationSpec,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },
        isSkipHalfExpanded = true
    ))
    val open: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    var isOpenBottomSheet = mutableStateOf(false)

    fun showBottomSheet() =  viewModelScope.launch {
        println("**************************************************************************")
        // sheetState.value.show()
        sheetState.value.animateTo(ModalBottomSheetValue.Expanded)
        println("value of the sheet************* ${sheetState.value.currentValue}")
        println("**************************************************************************")

    }

    fun hideBottomSheet() = run { viewModelScope.launch {sheetState.value.animateTo(ModalBottomSheetValue.Hidden)} }

    fun openBottomSheet(){
        isOpenBottomSheet.value = true
        println("value of itttttttttttttt ${isOpenBottomSheet}")
        open.value = true
    }

}