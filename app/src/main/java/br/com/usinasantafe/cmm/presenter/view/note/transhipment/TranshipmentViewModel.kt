package br.com.usinasantafe.cmm.presenter.view.note.transhipment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroEquipTranshipment
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.collectUpdateStep
import br.com.usinasantafe.cmm.utils.withFailure
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TranshipmentState(
    val flowApp: FlowApp = FlowApp.NOTE_WORK,
    val nroEquip: String = "",
    val flagAccess: Boolean = false,
    val status: UpdateStatusState = UpdateStatusState()
)

@HiltViewModel
class TranshipmentViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val updateTableEquip: UpdateTableEquip,
    private val hasEquipSecondary: HasEquipSecondary,
    private val setNroEquipTranshipment: SetNroEquipTranshipment,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(TranshipmentState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp]
            )
        }
    }

    private fun updateUi(block: TranshipmentState.() -> TranshipmentState) {
        _uiState.update { it.block() }
    }

    fun setCloseDialog() = updateUi {
        copy(
            status = status.copy(
                flagDialog = false,
                flagFailure = false
            )
        )
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val value = addTextField(uiState.value.nroEquip, text)
                _uiState.update {
                    it.copy(nroEquip = value)
                }
            }

            TypeButton.CLEAN -> {
                val value = clearTextField(uiState.value.nroEquip)
                _uiState.update {
                    it.copy(nroEquip = value)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.nroEquip.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            status = it.status.withFailure(
                                getClassAndMethod(),
                                "Field Empty!",
                                Errors.FIELD_EMPTY
                            )
                        )
                    }
                    return
                }
                setNroEquip()
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

    private fun setNroEquip() = viewModelScope.launch {
        val resultHas = hasEquipSecondary(
            nroEquip =  uiState.value.nroEquip,
            typeEquip = TypeEquip.TRANSHIPMENT
        )
        resultHas.onFailure { itThrowable ->
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
        val check = resultHas.getOrNull()!!
        if (check) {
            val resultSet = setNroEquipTranshipment(
                nroEquipTranshipment = uiState.value.nroEquip,
                flowApp = uiState.value.flowApp
            )
            resultSet.onFailure { itThrowable ->
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
        }
        _uiState.update {
            it.copy(
                flagAccess = check,
                status = it.status.copy(
                    flagDialog = !check,
                    flagFailure = !check,
                    errors = Errors.INVALID
                )
            )
        }
    }

    fun updateAllDatabase(): Flow<TranshipmentState> = flow {

        val size = 4f

        val steps = listOf(
            updateTableEquip(size, 1f),
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