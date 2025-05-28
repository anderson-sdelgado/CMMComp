package br.com.usinasantafe.cmm.presenter.header.activityList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.usecases.header.GetActivityList
import br.com.usinasantafe.cmm.domain.usecases.header.SetIdActivity
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableActivity
import br.com.usinasantafe.cmm.utils.Errors
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

data class ActivityListState(
    val activityList: List<Activity> = emptyList(),
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToActivityListState(): ActivityListState {
    val fail = if(failure.isNotEmpty()){
        val ret = "ActivityListViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "ActivityListViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return ActivityListState(
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
class ActivityListViewModel @Inject constructor(
    private val getActivityList: GetActivityList,
    private val updateTableActivity: UpdateTableActivity,
    private val setIdActivity: SetIdActivity,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun activityList() = viewModelScope.launch {
        val result = getActivityList()
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

    fun setIdActivityHeader(id: Int) = viewModelScope.launch {
        val result = setIdActivity(id)
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

    suspend fun updateAllDatabase(): Flow<ActivityListState> = flow {
        var pos = 0f
        val sizeAllUpdate = 4f
        var turnListState = ActivityListState()
        updateTableActivity(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            turnListState = it.resultUpdateToActivityListState()
            emit(
                it.resultUpdateToActivityListState()
            )
        }
        if (turnListState.flagFailure) return@flow
        emit(
            ActivityListState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }
}