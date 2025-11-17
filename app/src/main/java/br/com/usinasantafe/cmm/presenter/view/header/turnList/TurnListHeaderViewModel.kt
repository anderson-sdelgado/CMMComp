package br.com.usinasantafe.cmm.presenter.view.header.turnList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListTurn
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

fun ResultUpdateModel.toTurn(
    classAndMethod: String,
    current: TurnListHeaderState
): TurnListHeaderState {

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
        result.onFailure {
            handleFailure(it)
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

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    private suspend fun Flow<ResultUpdateModel>.collectUpdateStep(
        classAndMethod: String,
        emitState: suspend (TurnListHeaderState) -> Unit
    ): Boolean {
        var ok = true
        collect { result ->
            val newState = result.toTurn(classAndMethod, _uiState.value)
            emitState(newState)
            if (newState.flagFailure) {
                ok = false
                return@collect
            }
        }
        return ok
    }

    fun updateAllDatabase(): Flow<TurnListHeaderState> = flow {
        val size = 4f
        val classAndMethod = getClassAndMethod()

        val steps = listOf(
            updateTableTurn(size, 1f),
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(classAndMethod) { emit(it) }
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
    ): TurnListHeaderState {
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
    ): TurnListHeaderState {
        val msg = "${throwable.message} -> ${throwable.cause}"
        return handleFailure(msg, errors, emit)
    }

}