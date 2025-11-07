package br.com.usinasantafe.cmm.presenter.view.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquipByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemMenu
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableREquipActivityByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.utils.QTD_TABLE
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
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
)

fun ResultUpdateModel.resultUpdateToConfig(
    classAndMethod: String,
    currentState: ConfigState
): ConfigState {
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
        tableUpdate = this.tableUpdate,
    )
}

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateTableActivity: UpdateTableActivity,
    private val updateTableColab: UpdateTableColab,
    private val updateTableEquipByIdEquip: UpdateTableEquipByIdEquip,
    private val updateTableFunctionActivity: UpdateTableFunctionActivity,
    private val updateTableFunctionStop: UpdateTableFunctionStop,
    private val updateTableItemCheckListByNroEquip: UpdateTableItemCheckListByNroEquip,
    private val updateTableItemMenu: UpdateTableItemMenu,
    private val updateTableRActivityStop: UpdateTableRActivityStop,
    private val updateTableREquipActivityByIdEquip: UpdateTableREquipActivityByIdEquip,
    private val updateTableStop: UpdateTableStop,
    private val updateTableTurn: UpdateTableTurn,
    private val setFinishUpdateAllTable: SetFinishUpdateAllTable
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(
                flagDialog = false,
                flagFailure = false
            )
        }
    }

    fun onNumberChanged(number: String) {
        _uiState.update {
            it.copy(number = number)
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun onNroEquipChanged(nroEquip: String) {
        _uiState.update {
            it.copy(nroEquip = nroEquip)
        }
    }

    fun onCheckMotoMecChanged(checkMotoMec: Boolean) {
        _uiState.update {
            it.copy(checkMotoMec = checkMotoMec)
        }
    }

    fun setConfigMain(
        version: String,
        app: String
    ) {
        _uiState.update {
            it.copy(
                version = version,
                app = app
            )
        }
    }

    fun returnDataConfig() = viewModelScope.launch {
        val resultGetConfig = getConfigInternal()
        if (resultGetConfig.isFailure) {
            val error = resultGetConfig.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    errors = Errors.EXCEPTION,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val configModel = resultGetConfig.getOrNull()
        configModel?.let { config ->
            _uiState.update {
                it.copy(
                    number = config.number,
                    password = config.password,
                    nroEquip = config.nroEquip,
                    checkMotoMec = config.checkMotoMec,
                )
            }
        }
    }

    fun onSaveAndUpdate() {
        if (
            uiState.value.number.isEmpty() ||
            uiState.value.password.isEmpty() ||
            uiState.value.nroEquip.isEmpty()
        ) {
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.FIELD_EMPTY,
                    flagFailure = true,
                )
            }
            return
        }
        viewModelScope.launch {
            token().collect { configStateToken ->
                _uiState.value = configStateToken
                if (
                    (!configStateToken.flagFailure) &&
                    (configStateToken.currentProgress == 1f)
                ) {
                    updateAllDatabase().collect { configStateUpdate ->
                        _uiState.value = configStateUpdate
                    }
                }
            }
        }
    }

    fun token(): Flow<ConfigState> = flow {
        val sizeToken = 3f
        val number = uiState.value.number
        val password = uiState.value.password
        val nroEquip = uiState.value.nroEquip
        val app = uiState.value.app
        val version = uiState.value.version
        val checkMotoMec = uiState.value.checkMotoMec
        var lastEmittedState: ConfigState?
        lastEmittedState = _uiState.value
        emit(
            lastEmittedState.copy(
                flagProgress = true,
                levelUpdate = LevelUpdate.GET_TOKEN,
                currentProgress = percentage(1f, sizeToken)
            )
        )
        val resultSendDataConfig = sendDataConfig(
            number = number,
            password = password,
            nroEquip = nroEquip,
            app = app,
            version = version
        )
        if (resultSendDataConfig.isFailure) {
            val error = resultSendDataConfig.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            lastEmittedState = _uiState.value
            emit(
                lastEmittedState.copy(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        lastEmittedState = _uiState.value
        emit(
            lastEmittedState.copy(
                flagProgress = true,
                levelUpdate = LevelUpdate.SAVE_TOKEN,
                currentProgress = percentage(2f, sizeToken),
            )
        )
        val config = resultSendDataConfig.getOrNull()!!
        val resultSave = saveDataConfig(
            number = number,
            password = password,
            version = version,
            app = app,
            nroEquip = nroEquip,
            checkMotoMec = checkMotoMec,
            idBD = config.idServ!!,
            idEquip = config.idEquip!!,
        )
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            lastEmittedState = _uiState.value
            emit(
                lastEmittedState.copy(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        lastEmittedState = _uiState.value
        emit(
            lastEmittedState.copy(
                flagProgress = true,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL,
            )
        )
    }

    fun updateAllDatabase(): Flow<ConfigState> = flow {
        val sizeAllUpdate = sizeUpdate(QTD_TABLE)
        var lastEmittedState: ConfigState? = null
        val classAndMethod = getClassAndMethod()
        updateTableActivity(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = 2f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableEquipByIdEquip(
            sizeAll = sizeAllUpdate,
            count = 3f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableFunctionActivity(
            sizeAll = sizeAllUpdate,
            count = 4f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableFunctionStop(
            sizeAll = sizeAllUpdate,
            count = 5f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableItemCheckListByNroEquip(
            sizeAll = sizeAllUpdate,
            count = 6f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableItemMenu(
            sizeAll = sizeAllUpdate,
            count = 7f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableRActivityStop(
            sizeAll = sizeAllUpdate,
            count = 8f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableREquipActivityByIdEquip(
            sizeAll = sizeAllUpdate,
            count = 9f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableStop(
            sizeAll = sizeAllUpdate,
            count = 10f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        updateTableTurn(
            sizeAll = sizeAllUpdate,
            count = 11f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToConfig(
                classAndMethod,
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        val result = setFinishUpdateAllTable()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            lastEmittedState = _uiState.value
            emit(
                lastEmittedState!!.copy(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    failure = failure,
                    flagProgress = true,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        lastEmittedState = _uiState.value
        emit(
            lastEmittedState.copy(
                tableUpdate = "",
                flagDialog = true,
                flagProgress = true,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }
}