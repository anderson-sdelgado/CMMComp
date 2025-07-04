package br.com.usinasantafe.cmm.presenter.view.header.equip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.header.SetIdEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class EquipHeaderState(
    val description: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class EquipHeaderViewModel @Inject constructor(
    private val getDescrEquip: GetDescrEquip,
    private val setIdEquip: SetIdEquip
) : ViewModel() {

    private val _uiState = MutableStateFlow(EquipHeaderState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun getDescr() = viewModelScope.launch {
        val result = getDescrEquip()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val description = result.getOrNull()!!
        _uiState.update {
            it.copy(
                description = description,
            )
        }
    }

    fun setIdEquipHeader() = viewModelScope.launch {
        val result = setIdEquip()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = result.isSuccess,
            )
        }
    }

}