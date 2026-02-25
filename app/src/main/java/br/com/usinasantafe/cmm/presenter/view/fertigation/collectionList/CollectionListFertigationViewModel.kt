package br.com.usinasantafe.cmm.presenter.view.fertigation.collectionList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.fertigation.CheckCloseCollection
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListCollection
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollectionListFertigationState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val list: List<ItemValueOSScreenModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class CollectionListFertigationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val listCollection: ListCollection,
    private val checkCloseCollection: CheckCloseCollection,
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(CollectionListFertigationState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: CollectionListFertigationState.() -> CollectionListFertigationState) {
        _uiState.update(block)
    }
    
    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    init { updateState { copy(flowApp = FlowApp.entries[this@CollectionListFertigationViewModel.flowApp]) }}

    fun list() = viewModelScope.launch {
        runCatching {
            listCollection().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun checkClose() = viewModelScope.launch {
        runCatching {
            val check = checkCloseCollection().getOrThrow()
            if(!check) {
                handleFailure(getClassAndMethod(), Errors.INVALID_CLOSE_COLLECTION, ::onError)
                return@launch
            }
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String, errors: Errors = Errors.EXCEPTION) = updateState { copy(flagDialog = true, failure = failure, errors = errors) }

}