package org.goodexpert.apps.smartpay.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.goodexpert.apps.smartpay.model.CardDetails
import org.goodexpert.apps.smartpay.repository.Repository
import org.goodexpert.apps.smartpay.service.Service
import javax.inject.Inject

@HiltViewModel
class MotoViewModel @Inject constructor(
    private val repository: Repository,
    private val service: Service
) : BaseViewModel<MotoViewModel.Event, MotoViewModel.State, MotoViewModel.Effect>() {
    sealed class Event : UiEvent {
        data class Purchase(val amount: Number, val cardDetails: CardDetails, val isRecurring: Boolean) : Event()
    }

    data class State(
        val modified: Long
    ) : UiState;

    sealed class Effect : UiEffect {
        data class PurchasedResult(val isSuccess: Boolean, val error: Error? = null) : Effect()
        data class ShowError(val error: Error) : Effect()
    }

    override fun createInitialState(): State {
        return State(
            modified = 0
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.Purchase -> {
                onPurchase(event)
            }
        }
        setState { copy(modified = System.nanoTime()) }
    }

    private fun onPurchase(event: Event.Purchase) {
        viewModelScope.launch {
            if (event.isRecurring) {
                repository.save(event.cardDetails)
            }

            val result = service.purchase(event.amount, event.cardDetails)
            sendEffect { Effect.PurchasedResult(result) }
        }
    }

    fun purchase(amount: Number, cardDetails: CardDetails, isRecurring: Boolean = false) {
        viewModelScope.launch {
            postEvent(Event.Purchase(amount, cardDetails, isRecurring))
        }
    }
}