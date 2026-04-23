package br.com.usinasantafe.cmm.presenter.view.cec.release

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.cec.CheckIdRelease
import br.com.usinasantafe.cmm.domain.usecases.cec.SetIdRelease
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReleaseState(
    val idRelease: String = "",
    val flagEnd: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val checkIdRelease: CheckIdRelease,
    private val setIdRelease: SetIdRelease
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReleaseState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ReleaseState.() -> ReleaseState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }


    fun setTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(idRelease = addTextField(idRelease, text)) }
            TypeButton.CLEAN -> updateState { copy(idRelease = clearTextField(idRelease)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> Unit
        }
    }

    fun set() = viewModelScope.launch {
        runCatching {
            if (state.idRelease.isBlank()) {
                handleFailure(getClassAndMethod(), Errors.FIELD_EMPTY, ::onError)
                return@launch
            }
            val check = checkIdRelease(state.idRelease).getOrThrow()
            if (!check) {
                handleFailure(getClassAndMethod(), Errors.INVALID, ::onError)
                return@launch
            }
            setIdRelease(state.idRelease).getOrThrow()
        }
            .onSuccess {
                updateState { copy(flagAccess = true, flagDialog = false, flagEnd = it) }
            }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String, errors: Errors = Errors.EXCEPTION) = updateState { copy(flagDialog = true, failure = failure, errors = errors) }

}