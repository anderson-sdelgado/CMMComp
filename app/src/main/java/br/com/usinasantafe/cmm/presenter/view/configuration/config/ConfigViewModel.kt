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
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import br.com.usinasantafe.cmm.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val setFinishUpdateAllTable: SetFinishUpdateAllTable
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    private val state get() = _uiState.value

    private fun updateState(block: ConfigState.() -> ConfigState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState {
        copy(status = status.copy(flagDialog = false, flagFailure = false))
    }

    fun onNumberChanged(v: String) = updateState { copy(number = v) }
    fun onPasswordChanged(v: String) = updateState { copy(password = v) }
    fun onNroEquipChanged(v: String) = updateState { copy(nroEquip = v) }
    fun onCheckMotoMecChanged(v: Boolean) = updateState { copy(checkMotoMec = v) }

    fun setConfigMain(version: String, app: String) = updateState {
        copy(version = version, app = app)
    }

    fun returnDataConfig() = viewModelScope.launch {
        runCatching {
            getConfigInternal().getOrThrow()
        }
            .onSuccess { config ->
                config?.let {
                    updateState {
                        copy(
                            number = it.number,
                            password = it.password,
                            nroEquip = it.nroEquip,
                            checkMotoMec = it.checkMotoMec
                        )
                    }
                }
            }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    private fun ConfigState.isValid() =
        number.isNotBlank() && password.isNotBlank() && nroEquip.isNotBlank()

    fun onSaveAndUpdate() {
        if (!uiState.value.isValid()) {
            updateState {
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
                    updateAllDatabase().onCompletion {
                        if(!state.status.flagFailure){
                            setFinishUpdateAllTable().fold(
                                onSuccess = {
                                    emit(
                                        state.copy(
                                            status = state.status.copy(
                                                tableUpdate = "",
                                                flagDialog = true,
                                                flagProgress = true,
                                                flagFailure = false,
                                                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                                                currentProgress = 1f
                                            )
                                        )
                                    )
                                },
                                onFailure = {
                                    val newState = state.withFailure(getClassAndMethod(), it, flagProgress = true)
                                    emit(newState)
                                    _uiState.value = newState
                                }
                            )
                        }
                    }.collect { _uiState.value = it }
                }
            }
        }
    }

    fun token(): Flow<ConfigState> = flow {
        with(state) {
            runCatching {
                val sizeToken = 3f

                emit(
                    copy(
                        status = status.copy(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.GET_TOKEN,
                            currentProgress = percentage(1f, sizeToken)
                        )
                    )
                )

                val config =
                    sendDataConfig(
                        number = number,
                        password = password,
                        nroEquip = nroEquip,
                        app = app,
                        version = version
                    ).getOrThrow()

            emit(
                copy(
                    status = status.copy(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE_TOKEN,
                        currentProgress = percentage(2f, sizeToken)
                    )
                )
            )

            saveDataConfig(
                number = number,
                password = password,
                version = version,
                app = app,
                checkMotoMec = checkMotoMec,
                idServ = config.idServ ?: 0,
                equip = config.equip!!
            ).getOrThrow()

            }.onSuccess {
                emit(
                    copy(
                        status = status.copy(
                            flagProgress = true,
                            currentProgress = 1f,
                            levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL
                        )
                    )
                )
            }.onFailure {
                val newState = withFailure(getClassAndMethod(), it, Errors.TOKEN, flagProgress = true)
                emit(newState)
                _uiState.value = newState
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<ConfigState> =
        executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
            flagUpdateConfig = true
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