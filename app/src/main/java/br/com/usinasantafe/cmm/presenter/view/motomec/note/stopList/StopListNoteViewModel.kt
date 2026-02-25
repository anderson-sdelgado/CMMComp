package br.com.usinasantafe.cmm.presenter.view.motomec.note.stopList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListStop
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdStopNote
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableStop
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.view.motomec.common.activityList.ActivityListCommonState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class StopListNoteState(
    val list: List<StopScreenModel> = emptyList(),
    val field: String = "",
    val flagFilter: Boolean = false,
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<StopListNoteState> {

    override fun copyWithStatus(status: UpdateStatusState): StopListNoteState =
        copy(status = status)
}

@HiltViewModel
class StopListNoteViewModel @Inject constructor(
    private val updateTableRActivityStop: UpdateTableRActivityStop,
    private val updateTableStop: UpdateTableStop,
    private val listStop: ListStop,
    private val setIdStopNote: SetIdStopNote
) : ViewModel() {

    private val _uiState = MutableStateFlow(StopListNoteState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: StopListNoteState.() -> StopListNoteState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {
        if(!uiState.value.flagFilter) {
            runCatching {
                listStop().getOrThrow()
            }
                .onSuccess { updateState { copy(list = it) } }
                .onFailureUpdate(getClassAndMethod(), ::updateState)
        }
    }

    fun setIdStop(id: Int) = viewModelScope.launch {
        runCatching {
            setIdStopNote(id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun onFieldChanged(field: String) {
        val fieldUpper = field.uppercase()
        if(fieldUpper.isNotEmpty()){
            val stopListFilter = _uiState.value.list.filter {
                it.descr.contains(fieldUpper)
            }
            updateState { copy(list = stopListFilter, flagFilter = true) }
        } else {
            updateState { copy(flagFilter = false) }
            list()
        }
        updateState { copy(field = fieldUpper) }
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<StopListNoteState> =
        executeUpdateSteps(
            steps = listOf(updateTableRActivityStop(7f, 1f), updateTableStop(7f, 2f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}