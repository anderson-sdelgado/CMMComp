package br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.checkList.CheckUpdateCheckList
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.view.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
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

data class QuestionUpdateCheckListState(
    val flagCheckUpdate: Boolean = true,
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<QuestionUpdateCheckListState> {

    override fun copyWithStatus(status: UpdateStatusState): QuestionUpdateCheckListState =
        copy(status = status)
}

@HiltViewModel
class QuestionUpdateCheckListViewModel @Inject constructor(
    private val checkUpdateCheckList: CheckUpdateCheckList,
    private val updateTableItemCheckListByNroEquip: UpdateTableItemCheckListByNroEquip
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionUpdateCheckListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: QuestionUpdateCheckListState.() -> QuestionUpdateCheckListState) {
        _uiState.update(block)
    }

    fun setCloseDialog()  = updateState { copy(status = status.copy(flagDialog = false)) }

    fun checkUpdate() = viewModelScope.launch {
        runCatching {
            checkUpdateCheckList().getOrThrow()
        }
            .onSuccess { check -> updateState { copy(flagCheckUpdate = !check, flagAccess = !check) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }

    suspend fun updateAllDatabase(): Flow<QuestionUpdateCheckListState> =
        executeUpdateSteps(
            steps = listOf(updateTableItemCheckListByNroEquip(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}
