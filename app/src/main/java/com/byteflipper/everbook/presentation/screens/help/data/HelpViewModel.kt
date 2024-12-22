package com.byteflipper.everbook.presentation.screens.help.data

import androidx.compose.runtime.Composable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.byteflipper.everbook.domain.util.UIViewModel
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(

) : UIViewModel<HelpState, HelpEvent>() {

    companion object {
        @Composable
        fun getState() = getState<HelpViewModel, HelpState, HelpEvent>()

        @Composable
        fun getEvent() = getEvent<HelpViewModel, HelpState, HelpEvent>()
    }

    private val _state = MutableStateFlow(HelpState())
    override val state = _state.asStateFlow()

    override fun onEvent(event: HelpEvent) {
        when (event) {
            is HelpEvent.OnInit -> init(event)
        }
    }

    private fun init(event: HelpEvent.OnInit) {
        _state.update {
            it.copy(
                fromStart = event.screen.fromStart
            )
        }
    }
}