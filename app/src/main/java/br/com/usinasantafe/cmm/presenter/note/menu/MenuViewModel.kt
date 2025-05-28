package br.com.usinasantafe.cmm.presenter.note.menu

import androidx.lifecycle.ViewModel
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MenuState(
    val descrEquip: String = "",
    val menuList: List<ItemMenuModel> = listOf(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class MenuViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }



}