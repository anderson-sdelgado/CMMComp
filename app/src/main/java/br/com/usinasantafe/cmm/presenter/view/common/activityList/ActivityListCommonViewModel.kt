package br.com.usinasantafe.cmm.presenter.view.common.activityList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetTypeEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListActivity
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeEquip
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

data class ActivityListCommonState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val activityList: List<Activity> = emptyList(),
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun ResultUpdateModel.toActivity(
    classAndMethod: String,
    current: ActivityListCommonState
): ActivityListCommonState {

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
class ActivityListCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val listActivity: ListActivity,
    private val updateTableActivity: UpdateTableActivity,
    private val setIdActivityCommon: SetIdActivityCommon,
    private val getTypeEquip: GetTypeEquip
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(ActivityListCommonState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp]
            )
        }
    }

    fun activityList() = viewModelScope.launch {
        val result = listActivity()
        result.onFailure {
            handleFailure(it)
            return@launch
        }
        val list = result.getOrNull()!!
        _uiState.update {
            it.copy(
                activityList = list
            )
        }
    }

    fun setIdActivity(id: Int) = viewModelScope.launch {
        val result = setIdActivityCommon(
            id = id,
            flowApp = uiState.value.flowApp
        )
        result.onFailure {
            handleFailure(it)
            return@launch
        }
        val check = result.getOrNull()!!
        if(!check) {
            _uiState.update {
                it.copy(
                    flowApp = FlowApp.TRANSHIPMENT,
                    flagAccess = true
                )
            }
            return@launch
        }
        if(uiState.value.flowApp == FlowApp.HEADER_INITIAL){
            _uiState.update {
                it.copy(
                    flagAccess = true
                )
            }
            return@launch
        }
        val resultGetType = getTypeEquip()
        resultGetType.onFailure {
            handleFailure(it)
            return@launch
        }
        val typeEquip = resultGetType.getOrNull()!!
        if(typeEquip == TypeEquip.REEL_FERT) {
            _uiState.update {
                it.copy(
                    flowApp = FlowApp.REEL_FERT
                )
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = true
            )
        }
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    private suspend fun Flow<ResultUpdateModel>.collectUpdateStep(
        classAndMethod: String,
        emitState: suspend (ActivityListCommonState) -> Unit
    ): Boolean {
        var ok = true
        collect { result ->
            val newState = result.toActivity(classAndMethod, _uiState.value)
            emitState(newState)
            if (newState.flagFailure) {
                ok = false
                return@collect
            }
        }
        return ok
    }

    fun updateAllDatabase(): Flow<ActivityListCommonState> = flow {
        val size = 4f

        val steps = listOf(
            updateTableActivity(size, 1f),
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(getClassAndMethod()) { emit(it) }
            if (!ok) return@flow
        }

        emit(
            _uiState.value.copy(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }

    private fun handleFailure(
        message: String,
        errors: Errors = Errors.EXCEPTION,
        emit: Boolean = false
    ): ActivityListCommonState {
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
    ): ActivityListCommonState {
        val msg = "${throwable.message} -> ${throwable.cause}"
        return handleFailure(msg, errors, emit)
    }

}