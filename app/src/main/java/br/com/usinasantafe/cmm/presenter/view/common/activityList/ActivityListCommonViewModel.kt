package br.com.usinasantafe.cmm.presenter.view.common.activityList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.usecases.common.ListActivity
import br.com.usinasantafe.cmm.domain.usecases.common.SetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableActivity
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.LevelUpdate
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

fun ResultUpdateModel.resultUpdateToActivityListCommonState(): ActivityListCommonState {
    val fail = if(failure.isNotEmpty()){
        val ret = "ActivityListCommonViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return ActivityListCommonState(
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
class ActivityListCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val listActivity: ListActivity,
    private val updateTableActivity: UpdateTableActivity,
    private val setIdActivityCommon: SetIdActivityCommon,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!

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
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    flagFailure = true
                )
            }
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
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    flagFailure = true
                )
            }
            return@launch
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

    suspend fun updateAllDatabase(): Flow<ActivityListCommonState> = flow {
        var pos = 0f
        val sizeAllUpdate = 4f
        var activityListState = ActivityListCommonState()
        updateTableActivity(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            activityListState = it.resultUpdateToActivityListCommonState()
            emit(
                it.resultUpdateToActivityListCommonState()
            )
        }
        if (activityListState.flagFailure) return@flow
        emit(
            ActivityListCommonState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }
}