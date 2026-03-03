package br.com.usinasantafe.cmm.presenter.view.mechanic.itemList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.presenter.model.ItemOSMechanicModel
import br.com.usinasantafe.cmm.presenter.view.motomec.common.activityList.ActivityListCommonState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ItemListMechanicState(
    val list: List<ItemOSMechanicModel> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<ItemListMechanicState> {

    override fun copyWithStatus(status: UpdateStatusState): ItemListMechanicState =
        copy(status = status)
}

@HiltViewModel
class ItemListMechanicViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListMechanicState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ItemListMechanicState.() -> ItemListMechanicState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {

    }

    fun set(id: Int) = viewModelScope.launch {

    }

    fun updateDatabase() = viewModelScope.launch {

    }
}