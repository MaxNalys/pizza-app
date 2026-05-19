package com.dev.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.data.repository.PizzaRepository
import com.dev.model.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PizzaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadPizzas()
    }

    private fun loadPizzas() {
        viewModelScope.launch {

            try {

                _state.value = _state.value.copy(
                    isLoading = true
                )

                val pizzas: List<Pizza> = repository.getPizzas()

                _state.value = _state.value.copy(
                    isLoading = false,
                    pizzas = pizzas
                )

            } catch (e: Exception) {

                e.printStackTrace()

                _state.value = _state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    fun onPizzaChanged(index: Int) {
        _state.value = _state.value.copy(
            selectedPizzaIndex = index,
            quantity = 1
        )
    }

    fun onSizeSelected(size: String) {
        _state.value = _state.value.copy(
            selectedSize = size
        )
    }

    fun onPlusClick() {
        _state.value = _state.value.copy(
            quantity = _state.value.quantity + 1
        )
    }

    fun onMinusClick() {
        val quantity = (_state.value.quantity - 1)
            .coerceAtLeast(1)

        _state.value = _state.value.copy(
            quantity = quantity
        )
    }
}

data class HomeUiState(

    val isLoading: Boolean = false,

    val pizzas: List<Pizza> = emptyList(),

    val selectedPizzaIndex: Int = 0,

    val selectedSize: String = "M",

    val quantity: Int = 1

)