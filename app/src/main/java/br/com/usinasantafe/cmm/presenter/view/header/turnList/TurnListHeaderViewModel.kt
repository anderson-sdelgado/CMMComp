package br.com.usinasantafe.cmm.presenter.view.header.turnList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListTurn
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdTurn
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.collectUpdateStep
import br.com.usinasantafe.cmm.utils.withFailure
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TurnListHeaderState(
    val turnList: List<Turn> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<TurnListHeaderState> {

    override fun copyWithStatus(status: UpdateStatusState): TurnListHeaderState =
        copy(status = status)
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
            it.copy(
                status = it.status.copy(
                    flagDialog = false
                ),
            )
        }
    }

    fun turnList() = viewModelScope.launch {
        val result = listTurn()
        result.onFailure { itThrowable ->
            _uiState.update {
                it.copy(
                    status = it.status.withFailure(
                        getClassAndMethod(),
                        itThrowable
                    )
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
        result.onFailure { itThrowable ->
            _uiState.update {
                it.copy(
                    status = it.status.withFailure(
                        getClassAndMethod(),
                        itThrowable
                    )
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
        val size = 4f

        val steps = listOf(
            updateTableTurn(size, 1f),
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(
                classAndMethod = getClassAndMethod(),
                currentStatus = _uiState.value.status

            ) {
                emit(
                    _uiState.value.copy(
                        status = it
                    )
                )
            }
            if (!ok) return@flow
        }

        emit(
            _uiState.value.copy(
                status = _uiState.value.status.copy(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        )
    }

}