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
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
    val stopList: List<StopScreenModel> = emptyList(),
    val flagDialog: Boolean = false,
    val field: String = "",
    val flagFilter: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun UpdateStatusState.toStop(
    classAndMethod: String,
    current: StopListNoteState
): StopListNoteState {

    val failMsg = failure.takeIf { it.isNotEmpty() }
        ?.let { "$classAndMethod -> $it" }
        ?: ""

    if (failMsg.isNotEmpty()) Timber.e(failMsg)

    return current.copy(
        flagDialog = flagDialog,
        flagFailure = flagFailure,
        errors = errors,
        failure = failMsg,
        flagProgress = flagProgress,
        currentProgress = currentProgress,
        levelUpdate = levelUpdate,
        tableUpdate = tableUpdate
    )
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

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun stopList() = viewModelScope.launch {
        if(!uiState.value.flagFilter) {
            val result = listStop()
            result.onFailure {
                handleFailure(it)
                return@launch
            }
            val list = result.getOrNull()!!
            _uiState.update {
                it.copy(
                    stopList = list
                )
            }
        }
    }

    fun setIdStop(id: Int) = viewModelScope.launch {
        val result = setIdStopNote(id)
        result.onFailure {
            handleFailure(it)
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true
            )
        }
    }

    fun onFieldChanged(field: String) {
        val fieldUpper = field.uppercase()
        if(fieldUpper.isNotEmpty()){
            val stopListFilter = _uiState.value.stopList.filter {
                it.descr.contains(fieldUpper)
            }
            _uiState.update {
                it.copy(
                    stopList = stopListFilter,
                    flagFilter = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    flagFilter = false
                )
            }
            stopList()
        }
        _uiState.update {
            it.copy(
                field = fieldUpper
            )
        }
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    private suspend fun Flow<UpdateStatusState>.collectUpdateStep(
        classAndMethod: String,
        emitState: suspend (StopListNoteState) -> Unit
    ): Boolean {
        var ok = true
        collect { result ->
            val newState = result.toStop(classAndMethod, _uiState.value)
            emitState(newState)
            if (newState.flagFailure) {
                ok = false
                return@collect
            }
        }
        return ok
    }

    fun updateAllDatabase(): Flow<StopListNoteState> = flow {
        val size = 7f

        val steps = listOf(
            updateTableRActivityStop(size, 1f),
            updateTableStop(size, 2f),
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(getClassAndMethod()) { emit(it) }
            if (!ok) return@flow
        }


        emit(
            _uiState.value.copy(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }

    private fun handleFailure(
        message: String,
        errors: Errors = Errors.EXCEPTION,
        emit: Boolean = false
    ): StopListNoteState {
        val failMsg = "${getClassAndMethod()} -> $message"
        Timber.e(failMsg)

        val newState = _uiState.value.copy(
            flagDialog = true,
            flagFailure = true,
            failure = failMsg,
            errors = errors,
            flagProgress = false,
            currentProgress = 1f
        )

        if (!emit) {
            _uiState.value = newState
        }

        return newState
    }

    private fun handleFailure(
        throwable: Throwable,
        errors: Errors = Errors.EXCEPTION,
        emit: Boolean = false
    ): StopListNoteState {
        val msg = "${throwable.message} -> ${throwable.cause}"
        return handleFailure(msg, errors, emit)
    }

}