package br.com.usinasantafe.cmm.presenter.view.header.operator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.usecases.header.CheckRegOperator
import br.com.usinasantafe.cmm.domain.usecases.header.SetRegOperator
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableColab
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.TypeButton
import br.com.usinasantafe.cmm.utils.getClassAndMethodViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OperatorHeaderState(
    val regColab: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun ResultUpdateModel.resultUpdateToOperatorState(): OperatorHeaderState {
    val fail = if(failure.isNotEmpty()){
        val ret = "OperatorHeaderViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return OperatorHeaderState(
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
class OperatorHeaderViewModel @Inject constructor(
    private val updateTableColab: UpdateTableColab,
    private val checkRegOperator: CheckRegOperator,
    private val setRegOperator: SetRegOperator
) : ViewModel() {

    private val _uiState = MutableStateFlow(OperatorHeaderState())
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
                val matricColab = addTextField(uiState.value.regColab, text)
                _uiState.update {
                    it.copy(regColab = matricColab)
                }
            }

            TypeButton.CLEAN -> {
                val matricColab = clearTextField(uiState.value.regColab)
                _uiState.update {
                    it.copy(regColab = matricColab)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.regColab.isEmpty()) {
                    val failure = "OperatorHeaderViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELD_EMPTY,
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
        val resultCheck = checkRegOperator(uiState.value.regColab)
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethodViewModel()} -> ${error.message} -> ${error.cause.toString()}"
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
            val resultSet = setRegOperator(uiState.value.regColab)
            if (resultSet.isFailure) {
                val error = resultSet.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethodViewModel()} -> ${error.message} -> ${error.cause.toString()}"
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

    suspend fun updateAllDatabase(): Flow<OperatorHeaderState> = flow {
        var pos = 0f
        val sizeAllUpdate = 4f
        var operatorHeaderState = OperatorHeaderState()
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            operatorHeaderState = it.resultUpdateToOperatorState()
            emit(
                it.resultUpdateToOperatorState()
            )
        }
        if (operatorHeaderState.flagFailure) return@flow
        emit(
            OperatorHeaderState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }


}