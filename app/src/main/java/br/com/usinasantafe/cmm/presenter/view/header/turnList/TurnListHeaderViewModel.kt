package br.com.usinasantafe.cmm.presenter.view.header.turnList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.usecases.common.ListTurn
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdTurn
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
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

data class TurnListHeaderState(
    val turnList: List<Turn> = emptyList(),
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun ResultUpdateModel.resultUpdateToTurnListState(
    classAndMethod: String,
    currentState: TurnListHeaderState
): TurnListHeaderState {
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return currentState.copy(
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
class TurnListHeaderViewModel @Inject constructor(
    private val listTurn: ListTurn,
    private val setIdTurn: SetIdTurn,
    private val updateTableTurn: UpdateTableTurn
) : ViewModel() {

    private val _uiState = MutableStateFlow(TurnListHeaderState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun turnList() = viewModelScope.launch {
        val result = listTurn()
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
        val list = result.getOrNull()!!
        _uiState.update {
            it.copy(
                turnList = list
            )
        }
    }

    fun setIdTurnHeader(id: Int) = viewModelScope.launch {
        val result = setIdTurn(id)
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

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    fun updateAllDatabase(): Flow<TurnListHeaderState> = flow {
        val sizeAllUpdate = 4f
        var lastEmittedState: TurnListHeaderState? = null
        val classAndMethod = getClassAndMethod()
        updateTableTurn(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToTurnListState(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        lastEmittedState = _uiState.value
        emit(
            lastEmittedState.copy(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }
}