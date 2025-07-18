package br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.checkList.DelLastRespItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.checkList.GetItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.checkList.SetRespItemCheckList
import br.com.usinasantafe.cmm.utils.OptionRespCheckList
import br.com.usinasantafe.cmm.utils.getClassAndMethodViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ItemCheckListState(
    val pos: Int = 0,
    val id: Int = 0,
    val descr: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class ItemCheckListViewModel @Inject constructor(
    private val getItemCheckList: GetItemCheckList,
    private val setRespItemCheckList: SetRespItemCheckList,
    private val delLastRespItemCheckList: DelLastRespItemCheckList
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemCheckListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun get(
        pos: Int = 1
    ) = viewModelScope.launch {
        val result = getItemCheckList(pos)
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethodViewModel()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val model = result.getOrNull()!!
        _uiState.update {
            it.copy(
                pos = pos,
                id = model.id,
                descr = model.descr,
            )
        }
    }

    fun ret() = viewModelScope.launch {
        if(uiState.value.pos == 1) return@launch
        val result = delLastRespItemCheckList()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethodViewModel()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        get(pos = uiState.value.pos - 1)
    }

    fun set(
        option: OptionRespCheckList
    ) = viewModelScope.launch {
        val result = setRespItemCheckList(
            pos = uiState.value.pos,
            id = uiState.value.id,
            option = option
        )
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethodViewModel()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val check = result.getOrNull()!!
        if (check) {
            get(pos = uiState.value.pos + 1)
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
            )
        }
    }

}