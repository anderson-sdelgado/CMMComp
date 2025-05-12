package br.com.usinasantafe.cmm.presenter.header.operator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.usecases.header.CheckRegOperator
import br.com.usinasantafe.cmm.domain.usecases.header.SetRegOperator
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableColab
import br.com.usinasantafe.cmm.ui.theme.addTextField
import br.com.usinasantafe.cmm.ui.theme.clearTextField
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TypeButton
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

data class OperatorState(
    val matricColab: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToOperatorState(): OperatorState {
    val fail = if(failure.isNotEmpty()){
        val ret = "OperatorViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "OperatorViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return OperatorState(
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
class OperatorViewModel @Inject constructor(
    private val updateTableColab: UpdateTableColab,
    private val checkRegOperator: CheckRegOperator,
    private val setRegOperator: SetRegOperator
) : ViewModel() {

    private val _uiState = MutableStateFlow(OperatorState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val matricColab = addTextField(uiState.value.matricColab, text)
                _uiState.update {
                    it.copy(matricColab = matricColab)
                }
            }

            TypeButton.CLEAN -> {
                val matricColab = clearTextField(uiState.value.matricColab)
                _uiState.update {
                    it.copy(matricColab = matricColab)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.matricColab.isEmpty()) {
                    val failure = "OperatorViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY,
                            failure = failure,
                            flagAccess = false
                        )
                    }
                    return
                }
                setRegOperatorHeader()
            }

            TypeButton.UPDATE -> {
                viewModelScope.launch {
                    updateAllDatabase().collect { stateUpdate ->
                        _uiState.value = stateUpdate
                    }
                }
            }
        }
    }

    private fun setRegOperatorHeader() = viewModelScope.launch {
        val resultCheck = checkRegOperator(uiState.value.matricColab)
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                )
            }
            return@launch
        }
        val check = resultCheck.getOrNull()!!
        if (check) {
            val resultSet = setRegOperator(uiState.value.matricColab)
            if (resultSet.isFailure) {
                val error = resultSet.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                    )
                }
                return@launch
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = check,
                flagDialog = !check,
                flagFailure = !check,
                errors = Errors.INVALID
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<OperatorState> = flow {
        var pos = 0f
        val sizeAllUpdate = 4f
        var operatorState = OperatorState()
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            operatorState = it.resultUpdateToOperatorState()
            emit(
                it.resultUpdateToOperatorState()
            )
        }
        if (operatorState.flagFailure) return@flow
        emit(
            OperatorState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }


}