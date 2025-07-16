package br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.checkList.CheckUpdateCheckList
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.presenter.view.common.activityList.ActivityListCommonState
import br.com.usinasantafe.cmm.presenter.view.common.activityList.resultUpdateToActivityListCommonState
import br.com.usinasantafe.cmm.presenter.view.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.Errors
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

data class QuestionUpdateCheckListState(
    val flagCheckUpdate: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagFailure: Boolean = false,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun ResultUpdateModel.resultUpdateToActivityListCommonState(): QuestionUpdateCheckListState {
    val fail = if(failure.isNotEmpty()){
        val ret = "QuestionUpdateCheckListViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return QuestionUpdateCheckListState(
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
class QuestionUpdateCheckListViewModel @Inject constructor(
    private val checkUpdateCheckList: CheckUpdateCheckList
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionUpdateCheckListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun checkUpdate() = viewModelScope.launch {
        val result = checkUpdateCheckList()
        if (result.isFailure) {
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
        val check = result.getOrNull()!!
        _uiState.update {
            it.copy(
                flagCheckUpdate = !check,
                flagAccess = !check
            )
        }
    }

    fun updateDatabase() = viewModelScope.launch {

    }


    fun updateAllDatabase(): Flow<ActivityListCommonState> = flow {
//        var pos = 0f
//        val sizeAllUpdate = 4f
//        var activityListState = ActivityListCommonState()
//        updateTableActivity(
//            sizeAll = sizeAllUpdate,
//            count = ++pos
//        ).collect {
//            activityListState = it.resultUpdateToActivityListCommonState()
//            emit(
//                it.resultUpdateToActivityListCommonState()
//            )
//        }
//        if (activityListState.flagFailure) return@flow
//        emit(
//            ActivityListCommonState(
//                flagDialog = true,
//                flagProgress = false,
//                flagFailure = false,
//                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
//                currentProgress = 1f,
//            )
//        )
    }
}
