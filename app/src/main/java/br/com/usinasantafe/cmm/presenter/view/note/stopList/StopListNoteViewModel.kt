package br.com.usinasantafe.cmm.presenter.view.note.stopList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.usecases.common.ListStop
import br.com.usinasantafe.cmm.domain.usecases.note.SetIdStopNote
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableStop
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
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

fun ResultUpdateModel.resultUpdateToStopListNoteState(): StopListNoteState {
    val fail = if(failure.isNotEmpty()){
        val ret = "StopListNoteViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return StopListNoteState(
        flagDialog = this.flagDialog,
        flagFailure = this.flagFailure,
        errors = this.errors,
        failure = fail,
        flagProgress = this.flagProgress,
        currentProgress = this.currentProgress,
        levelUpdate = this.levelUpdate,
        tableUpdate = this.tableUpdate
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
            if (result.isFailure) {
                val error = result.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                        flagFailure = true
                    )
                }
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
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    flagFailure = true
                )
            }
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

    suspend fun updateAllDatabase(): Flow<StopListNoteState> = flow {
        var pos = 0f
        val sizeAllUpdate = 7f
        var stopListState = StopListNoteState()
        updateTableRActivityStop(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            stopListState = it.resultUpdateToStopListNoteState()
            emit(
                it.resultUpdateToStopListNoteState()
            )
        }
        if (stopListState.flagFailure) return@flow
        updateTableStop(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            stopListState = it.resultUpdateToStopListNoteState()
            emit(
                it.resultUpdateToStopListNoteState()
            )
        }
        if (stopListState.flagFailure) return@flow
        emit(
            StopListNoteState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }
}