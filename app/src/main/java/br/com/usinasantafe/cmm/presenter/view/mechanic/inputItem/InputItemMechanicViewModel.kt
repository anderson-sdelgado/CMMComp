package br.com.usinasantafe.cmm.presenter.view.mechanic.inputItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.mechanic.SetSeqItem
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InputItemMechanicState(
    val seqItem: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class InputItemMechanicViewModel @Inject constructor(
    private val setSeqItem: SetSeqItem
) : ViewModel() {

    private val _uiState = MutableStateFlow(InputItemMechanicState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: InputItemMechanicState.() -> InputItemMechanicState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(seqItem = addTextField(seqItem, text)) }
            TypeButton.CLEAN -> updateState { copy(seqItem = clearTextField(seqItem)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {}
        }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            if (state.seqItem.isBlank()) {
                handleFailure(getClassAndMethod(), Errors.FIELD_EMPTY, ::onError)
                return@launch
            }
            val seqItem = state.seqItem.toInt()
            if(seqItem >= 1000) {
                handleFailure(getClassAndMethod(), Errors.INVALID_VALUE, ::onError)
                return@launch
            }
            setSeqItem(seqItem).getOrThrow()
        }
            .onSuccess {
                updateState { copy(flagAccess = true, flagDialog = false) }
            }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String, error: Errors = Errors.EXCEPTION) = updateState {
        copy(flagDialog = true, failure = failure, errors = error, flagAccess = false)
    }


}