package br.com.usinasantafe.cmm.presenter.header.turnlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.usecases.header.GetTurnList
import br.com.usinasantafe.cmm.domain.usecases.header.SetIdTurn
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableTurn
import br.com.usinasantafe.cmm.utils.Errors
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
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToTurnListState(): TurnListHeaderState {
    val fail = if(failure.isNotEmpty()){
        val ret = "TurnListHeaderViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "TurnListHeaderViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return TurnListHeaderState(
        flagDialog = this.flagDialog,
        flagFailure = this.flagFailure,
        errors = this.errors,
        failure = fail,
        flagProgress = this.flagProgress,
        msgProgress = msg,
        currentProgress = this.currentProgress,
    )
}

@HiltViewModel
class TurnListHeaderViewModel @Inject constructor(
    private val getTurnList: GetTurnList,
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
        val result = getTurnList()
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

    suspend fun updateAllDatabase(): Flow<TurnListHeaderState> = flow {
        var pos = 0f
        val sizeAllUpdate = 4f
        var turnListHeaderState = TurnListHeaderState()
        updateTableTurn(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            turnListHeaderState = it.resultUpdateToTurnListState()
            emit(
                it.resultUpdateToTurnListState()
            )
        }
        if (turnListHeaderState.flagFailure) return@flow
        emit(
            TurnListHeaderState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }
}