package br.com.usinasantafe.cmm.presenter.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableAtividade
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableBocal
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableColab
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableComponente
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableFrente
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableItemOSMecan
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableLeira
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableMotoMec
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableOS
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTablePressaoBocal
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTablePropriedade
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableRAtivParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableREquipPneu
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableRFuncaoAtivParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableROSAtiv
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableServico
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableTurno
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.percentage
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
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELDEMPTY,
)

fun ResultUpdate.resultUpdateToConfig(): ConfigState {
    val fail = if(failure.isNotEmpty()){
        val ret = "ConfigViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "ConfigViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return ConfigState(
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
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateTableAtividade: UpdateTableAtividade,
    private val updateTableBocal: UpdateTableBocal,
    private val updateTableColab: UpdateTableColab,
    private val updateTableComponente: UpdateTableComponente,
    private val updateTableFrente: UpdateTableFrente,
    private val updateTableItemCheckList: UpdateTableItemCheckList,
    private val updateTableItemOSMecan: UpdateTableItemOSMecan,
    private val updateTableLeira: UpdateTableLeira,
    private val updateTableMotoMec: UpdateTableMotoMec,
    private val updateTableOS: UpdateTableOS,
    private val updateTableParada: UpdateTableParada,
    private val updateTablePressaoBocal: UpdateTablePressaoBocal,
    private val updateTablePropriedade: UpdateTablePropriedade,
    private val updateTableRAtivParada: UpdateTableRAtivParada,
    private val updateTableREquipPneu: UpdateTableREquipPneu,
    private val updateTableRFuncaoAtivParada: UpdateTableRFuncaoAtivParada,
    private val updateTableROSAtiv: UpdateTableROSAtiv,
    private val updateTableServico: UpdateTableServico,
    private val updateTableTurno: UpdateTableTurno,
    private val setCheckUpdateAllTable: SetCheckUpdateAllTable
) : ViewModel() {

    private val qtdTable = 21f

    private val tag = javaClass.simpleName

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
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
                "${tag}.returnDataConfig -> GetConfigInternal -> ${error.message} -> ${error.cause.toString()}"
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

    fun saveTokenAndUpdate() {
        if (
            uiState.value.number.isEmpty() ||
            uiState.value.password.isEmpty() ||
            uiState.value.nroEquip.isEmpty()
        ) {
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.FIELDEMPTY,
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

    suspend fun token(): Flow<ConfigState> = flow {
        val sizeToken = 3f
        val number = uiState.value.number
        val password = uiState.value.password
        val nroEquip = uiState.value.nroEquip
        val app = uiState.value.app
        val version = uiState.value.version
        val checkMotoMec = uiState.value.checkMotoMec
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
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
                "${getClassAndMethod()} -> SendDataConfig -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = percentage(2f, sizeToken),
            )
        )
        val idBD = resultSendDataConfig.getOrNull()!!
        val resultSave = saveDataConfig(
            number = number,
            password = password,
            version = version,
            app = app,
            nroEquip = nroEquip,
            checkMotoMec = checkMotoMec,
            idBD = idBD
        )
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> SaveDataConfig -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Ajuste iniciais finalizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllDatabase(): Flow<ConfigState> = flow {
        var pos = 0f
        val sizeAllUpdate = sizeUpdate(qtdTable)
        var configState = ConfigState()
        updateTableAtividade(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableBocal(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableComponente(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableFrente(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableItemCheckList(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableItemOSMecan(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableLeira(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableMotoMec(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableOS(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableParada(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTablePressaoBocal(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTablePropriedade(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableRAtivParada(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableREquipPneu(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableRFuncaoAtivParada(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableROSAtiv(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableServico(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableTurno(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        val result = setCheckUpdateAllTable(FlagUpdate.UPDATED)
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} > SetCheckUpdateAllTable -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    failure = failure,
                    flagProgress = true,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagDialog = true,
                flagProgress = true,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }
}