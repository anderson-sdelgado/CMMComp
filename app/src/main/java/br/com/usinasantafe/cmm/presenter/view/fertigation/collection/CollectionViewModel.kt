package br.com.usinasantafe.cmm.presenter.view.fertigation.collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetCollection
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.Args.ID_ARG
import br.com.usinasantafe.cmm.presenter.theme.addTextFieldComma
import br.com.usinasantafe.cmm.presenter.theme.clearTextFieldComma
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollectionState(
    val id: Int = 0,
    val nroOS: String = "",
    val collection: String = "0,0",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class CollectionViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val setCollection: SetCollection
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARG]!!

    private val _uiState = MutableStateFlow(CollectionState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: CollectionState.() -> CollectionState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun setTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(collection = addTextFieldComma(collection, text)) }
            TypeButton.CLEAN -> updateState { copy(collection = clearTextFieldComma(collection)) }
            TypeButton.OK -> validateAndSet()
            TypeButton.UPDATE -> Unit
        }
    }

    private fun validateAndSet() {
        if (uiState.value.collection == "0,0") {
            handleFailure(Errors.FIELD_EMPTY, getClassAndMethod(), ::onError)
            return
        }
        set()
    }

    fun set() =
        viewModelScope.launch {
            runCatching {
                setCollection(id, state.collection).getOrThrow()
            }
                .onSuccess {
                    updateState { copy(flagAccess = true, flagDialog = false) }
                }
                .onFailureHandled(getClassAndMethod(), ::onError)
        }

    private fun onError(failure: String, errors: Errors = Errors.EXCEPTION) = updateState { copy(flagDialog = true, failure = failure, errors = errors) }

}