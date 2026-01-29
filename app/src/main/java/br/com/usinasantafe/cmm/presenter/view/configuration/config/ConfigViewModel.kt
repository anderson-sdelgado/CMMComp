package br.com.usinasantafe.cmm.presenter.view.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemMenu
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableREquipActivityByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRItemMenuStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.lib.QTD_TABLE
import br.com.usinasantafe.cmm.presenter.view.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ConfigState(
    val number: String = "",
    val password: String = "",
    val nroEquip: String = "",
    val checkMotoMec: Boolean = true,
    val app: String = "",
    val version: String = "",
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<ConfigState> {

    override fun copyWithStatus(status: UpdateStatusState): ConfigState =
        copy(status = status)
}

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateTableActivity: UpdateTableActivity,
    private val updateTableColab: UpdateTableColab,
    private val updateTableEquip: UpdateTableEquip,
    private val updateTableFunctionActivity: UpdateTableFunctionActivity,
    private val updateTableFunctionStop: UpdateTableFunctionStop,
    private val updateTableItemCheckListByNroEquip: UpdateTableItemCheckListByNroEquip,
    private val updateTableItemMenu: UpdateTableItemMenu,
    private val updateTableRActivityStop: UpdateTableRActivityStop,
    private val updateTableREquipActivityByIdEquip: UpdateTableREquipActivityByIdEquip,
    private val updateTableRItemMenuStop: UpdateTableRItemMenuStop,
    private val updateTableStop: UpdateTableStop,
    private val updateTableTurn: UpdateTableTurn,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    private val state get() = _uiState.value

    private fun updateUi(block: ConfigState.() -> ConfigState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateUi {
        copy(status = status.copy(flagDialog = false, flagFailure = false))
    }

    fun onNumberChanged(v: String) = updateUi { copy(number = v) }
    fun onPasswordChanged(v: String) = updateUi { copy(password = v) }
    fun onNroEquipChanged(v: String) = updateUi { copy(nroEquip = v) }
    fun onCheckMotoMecChanged(v: Boolean) = updateUi { copy(checkMotoMec = v) }

    fun setConfigMain(version: String, app: String) = updateUi {
        copy(version = version, app = app)
    }

    fun returnDataConfig() = viewModelScope.launch {
        getConfigInternal().fold(
            onSuccess = { config ->
                config?.let {
                    updateUi {
                        copy(
                            number = it.number,
                            password = it.password,
                            nroEquip = it.nroEquip,
                            checkMotoMec = it.checkMotoMec
                        )
                    }
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.withFailure(getClassAndMethod(), throwable)
                }
            }
        )
    }

    private fun ConfigState.isValid() =
        number.isNotBlank() && password.isNotBlank() && nroEquip.isNotBlank()

    fun onSaveAndUpdate() {
        if (!uiState.value.isValid()) {
            updateUi {
                copy(
                    status = status.copy(
                        flagDialog = true,
                        errors = Errors.FIELD_EMPTY,
                        flagFailure = true
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            token().collect { state ->
                if (!state.status.flagFailure && state.status.currentProgress == 1f) {
                    updateAllDatabase().collect { _uiState.value = it }
                }
            }
        }
    }

    fun token(): Flow<ConfigState> = flow {

        runCatching {
        val sizeToken = 3f

        emit(
            state.copy(
                status = state.status.copy(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, sizeToken)
                )
            )
        )

        val config = sendDataConfig(
            number = state.number,
            password = state.password,
            nroEquip = state.nroEquip,
            app = state.app,
            version = state.version
        ).getOrThrow()

        emit(
            state.copy(
                status = state.status.copy(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, sizeToken)
                )
            )
        )

        saveDataConfig(
            number = state.number,
            password = state.password,
            version = state.version,
            app = state.app,
            checkMotoMec = state.checkMotoMec,
            idServ = config.idServ ?: 0,
            equip = config.equip!!
        ).getOrThrow()

        }.onSuccess {
            emit(
                state.copy(
                    status = state.status.copy(
                        flagProgress = true,
                        currentProgress = 1f,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL
                    )
                )
            )
        }.onFailure {
            emit(state.withFailure(getClassAndMethod(), it, Errors.TOKEN))
        }
    }

    suspend fun updateAllDatabase(): Flow<ConfigState> =
        executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

    private suspend fun listUpdate() : List<Flow<UpdateStatusState>> {
        val size = sizeUpdate(QTD_TABLE)
        return listOf(
            updateTableActivity(size, 1f),
            updateTableColab(size, 2f),
            updateTableEquip(size, 3f),
            updateTableFunctionActivity(size, 4f),
            updateTableFunctionStop(size, 5f),
            updateTableItemCheckListByNroEquip(size, 6f),
            updateTableItemMenu(size, 7f),
            updateTableRActivityStop(size, 8f),
            updateTableREquipActivityByIdEquip(size, 9f),
            updateTableRItemMenuStop(size, 10f),
            updateTableStop(size, 11f),
            updateTableTurn(size, 12f)
        )
    }

}