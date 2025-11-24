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

fun ResultUpdateModel.toConfig(
    classAndMethod: String,
    current: ConfigState
): ConfigState {

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

    private fun updateUi(block: ConfigState.() -> ConfigState) {
        _uiState.update { it.block() }
    }


    fun setCloseDialog() = updateUi {
        copy(flagDialog = false, flagFailure = false)
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
            onFailure = {
                handleFailure(it)
            }
        )
    }

    private fun ConfigState.isValid() =
        number.isNotBlank() && password.isNotBlank() && nroEquip.isNotBlank()

    fun onSaveAndUpdate() {
        if (!uiState.value.isValid()) {
            updateUi {
                copy(
                    flagDialog = true,
                    errors = Errors.FIELD_EMPTY,
                    flagFailure = true
                )
            }
            return
        }

        viewModelScope.launch {
            token().collect { state ->
                _uiState.value = state
                if (!state.flagFailure && state.currentProgress == 1f) {
                    updateAllDatabase().collect { _uiState.value = it }
                }
            }
        }
    }

    fun token(): Flow<ConfigState> = flow {

        val s = uiState.value
        val sizeToken = 3f

        emit(s.copy(
            flagProgress = true,
            levelUpdate = LevelUpdate.GET_TOKEN,
            currentProgress = percentage(1f, sizeToken)
        ))

        val sendResult = sendDataConfig(
            number = s.number,
            password = s.password,
            nroEquip = s.nroEquip,
            app = s.app,
            version = s.version
        )

        val config = sendResult.getOrElse {
            emit(handleFailure(it, Errors.TOKEN, true))
            return@flow
        }

        emit(
            _uiState.value.copy(
                flagProgress = true,
                levelUpdate = LevelUpdate.SAVE_TOKEN,
                currentProgress = percentage(2f, sizeToken)
            )
        )

        val saveResult = saveDataConfig(
            number = s.number,
            password = s.password,
            version = s.version,
            app = s.app,
            nroEquip = s.nroEquip,
            checkMotoMec = s.checkMotoMec,
            idBD = config.idServ ?: 0,
            idEquip = config.idEquip ?: 0
        )

        saveResult.getOrElse {
            emit(handleFailure(it, Errors.TOKEN, true))
            return@flow
        }

        emit(
            _uiState.value.copy(
                flagProgress = true,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL
            )
        )
    }

    private suspend fun Flow<ResultUpdateModel>.collectUpdateStep(
        classAndMethod: String,
        emitState: suspend (ConfigState) -> Unit
    ): Boolean {
        var ok = true
        collect { result ->
            val newState = result.toConfig(classAndMethod, _uiState.value)
            emitState(newState)
            if (newState.flagFailure) {
                ok = false
                return@collect
            }
        }
        return ok
    }

    fun updateAllDatabase(): Flow<ConfigState> = flow {
        val size = sizeUpdate(QTD_TABLE)

        val steps = listOf(
            updateTableActivity(size, 1f),
            updateTableColab(size, 2f),
            updateTableEquipByIdEquip(size, 3f),
            updateTableFunctionActivity(size, 4f),
            updateTableFunctionStop(size, 5f),
            updateTableItemCheckListByNroEquip(size, 6f),
            updateTableItemMenu(size, 7f),
            updateTableRActivityStop(size, 8f),
            updateTableREquipActivityByIdEquip(size, 9f),
            updateTableStop(size, 10f),
            updateTableTurn(size, 11f)
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(getClassAndMethod()) { emit(it) }
            if (!ok) return@flow
        }

        setFinishUpdateAllTable().fold(
            onSuccess = {
                emit(_uiState.value.copy(
                    tableUpdate = "",
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f
                ))
            },
            onFailure = {
                emit(handleFailure(it, emit = true))
            }
        )
    }

    private fun handleFailure(
        message: String,
        errors: Errors = Errors.EXCEPTION,
        emit: Boolean = false
    ): ConfigState {
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
    ): ConfigState {
        val msg = "${throwable.message} -> ${throwable.cause}"
        return handleFailure(msg, errors, emit)
    }

}