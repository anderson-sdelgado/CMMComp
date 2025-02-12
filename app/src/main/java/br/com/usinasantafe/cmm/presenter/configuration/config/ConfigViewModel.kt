package br.com.usinasantafe.cmm.presenter.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateAtividade
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateBocal
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateColab
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateComponente
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateEquipPneu
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateEquipSeg
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateFrente
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateItemOSMecan
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateLeira
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateMotoMec
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateOS
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdatePressaoBocal
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdatePropriedade
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateRAtivParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateREquipPneu
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateRFuncaoAtiv
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateROSAtiv
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateServico
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateTurno
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELDEMPTY,
)

fun ResultUpdate.resultUpdateToConfig(): ConfigState {
    return with(this) {
        ConfigState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = if(this.failure == "") "" else "ConfigViewModel.update -> ${this.failure}",
            flagProgress = this.flagProgress,
            msgProgress = this.msgProgress,
            currentProgress = this.currentProgress,
        )
    }
}

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateAtividade: UpdateAtividade,
    private val updateBocal: UpdateBocal,
    private val updateColab: UpdateColab,
    private val updateComponente: UpdateComponente,
    private val updateEquipPneu: UpdateEquipPneu,
    private val updateEquipSeg: UpdateEquipSeg,
    private val updateFrente: UpdateFrente,
    private val updateItemCheckList: UpdateItemCheckList,
    private val updateItemOSMecan: UpdateItemOSMecan,
    private val updateLeira: UpdateLeira,
    private val updateMotoMec: UpdateMotoMec,
    private val updateOS: UpdateOS,
    private val updateParada: UpdateParada,
    private val updatePressaoBocal: UpdatePressaoBocal,
    private val updatePropriedade: UpdatePropriedade,
    private val updateRAtivParada: UpdateRAtivParada,
    private val updateREquipPneu: UpdateREquipPneu,
    private val updateRFuncaoAtiv: UpdateRFuncaoAtiv,
    private val updateROSAtiv: UpdateROSAtiv,
    private val updateServico: UpdateServico,
    private val updateTurno: UpdateTurno,
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
                "${tag}.token -> SendDataConfig -> ${error.message} -> ${error.cause.toString()}"
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
                "${tag}.token -> SaveDataConfig -> ${error.message} -> ${error.cause.toString()}"
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
        updateAtividade(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateBocal(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateColab(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateComponente(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateEquipPneu(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateEquipSeg(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateFrente(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateItemCheckList(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateItemOSMecan(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateLeira(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateMotoMec(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateOS(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateParada(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updatePressaoBocal(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updatePropriedade(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateRAtivParada(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateREquipPneu(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateRFuncaoAtiv(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateROSAtiv(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateServico(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTurno(
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
                "${tag}.update -> SetCheckUpdateAllTable -> ${error.message} -> ${error.cause.toString()}"
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