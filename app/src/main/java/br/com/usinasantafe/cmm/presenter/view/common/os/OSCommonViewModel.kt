package br.com.usinasantafe.cmm.presenter.view.common.os

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.config.GetApp
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNroOS
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetNroOSHeader
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroOS
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableOS
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableROSActivity
import br.com.usinasantafe.cmm.lib.App
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import br.com.usinasantafe.cmm.utils.sizeUpdate
import br.com.usinasantafe.cmm.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OSCommonState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val app: App = App.PMM,
    val nroOS: String = "",
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<OSCommonState> {

    override fun copyWithStatus(status: UpdateStatusState): OSCommonState =
        copy(status = status)
}

@HiltViewModel
class OSCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val hasNroOS: HasNroOS,
    private val setNroOS: SetNroOS,
    private val getNroOSHeader: GetNroOSHeader,
    private val getApp: GetApp,
    private val updateTableOS: UpdateTableOS,
    private val updateTableROSActivity: UpdateTableROSActivity
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(OSCommonState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: OSCommonState.() -> OSCommonState) {
        _uiState.update(block)
    }

    init { updateState { copy(flowApp = FlowApp.entries[this@OSCommonViewModel.flowApp]) } }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(nroOS = addTextField(nroOS, text)) }
            TypeButton.CLEAN -> updateState { copy(nroOS = clearTextField(nroOS)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    fun start() = viewModelScope.launch {
            runCatching {
                var nroOS = ""
                if((uiState.value.flowApp != FlowApp.HEADER_INITIAL) && (uiState.value.flowApp != FlowApp.PRE_CEC)) {
                    nroOS = getNroOSHeader().getOrThrow()
                }
                val app = getApp().getOrThrow()
                nroOS to app
            }
                .onSuccess{ (nroOS, app) ->
                    updateState { copy(nroOS = nroOS, app = app) }
                }
                .onFailureUpdate(getClassAndMethod(), ::updateState)

    }

    private fun set() = viewModelScope.launch {
        runCatching {
            if (state.nroOS.isBlank()) {
                updateState { withFailure(getClassAndMethod(), Errors.FIELD_EMPTY) }
                return@launch
            }

            updateState { copy(status = status.copy(flagProgress = true, levelUpdate = LevelUpdate.CHECK_OS)) }
            val check = hasNroOS(nroOS = uiState.value.nroOS, flowApp = uiState.value.flowApp).getOrThrow()
            if(!check){
                updateState { withFailure(getClassAndMethod(), Errors.INVALID) }
                return@launch
            }
            setNroOS(nroOS = uiState.value.nroOS).getOrThrow()
        }
            .onSuccess {
                updateState {
                    copy(
                        flagAccess = true,
                        status = status.copy(
                            flagDialog = false,
                            flagProgress = false
                        )
                    )
                }
            }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    suspend fun updateAllDatabase(): Flow<OSCommonState> =
        executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

    private suspend fun listUpdate() : List<Flow<UpdateStatusState>> {
        val size = sizeUpdate(2f)
        return listOf(
            updateTableOS(size, 1f),
            updateTableROSActivity(size, 2f)
        )
    }
}