package br.com.usinasantafe.cmm.presenter.view.mechanic.itemList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.mechanic.ListItemOS
import br.com.usinasantafe.cmm.domain.usecases.mechanic.SetSeqItem
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemOSMechanicByIdEquipAndNroOS
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.cmm.presenter.model.ItemOSMechanicModel
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import br.com.usinasantafe.cmm.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val listItemOS: ListItemOS,
    private val updateTableItemOSMechanicByIdEquipAndNroOS: UpdateTableItemOSMechanicByIdEquipAndNroOS,
    private val updateTableComponent: UpdateTableComponent,
    private val updateTableService: UpdateTableService,
    private val setSeqItem: SetSeqItem
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListMechanicState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: ItemListMechanicState.() -> ItemListMechanicState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {
        runCatching {
            listItemOS().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun set(seqItem: Int) = viewModelScope.launch {
        runCatching {
            setSeqItem(seqItem).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<ItemListMechanicState> =
        executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

    private suspend fun listUpdate() : List<Flow<UpdateStatusState>> {
        val size = sizeUpdate(3f)
        return listOf(
            updateTableItemOSMechanicByIdEquipAndNroOS(size, 1f),
            updateTableComponent(size, 2f),
            updateTableService(size, 3f)
        )
    }
}