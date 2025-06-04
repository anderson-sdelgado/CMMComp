package br.com.usinasantafe.cmm.presenter.note.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.ItemListDesign
import br.com.usinasantafe.cmm.ui.theme.TextButtonDesign
import br.com.usinasantafe.cmm.ui.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowMenu

@Composable
fun MenuNoteScreen(
    viewModel: MenuNoteViewModel = hiltViewModel(),
    onNavOS: () -> Unit,
    onNavActivityList: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuNoteContent(
                descrEquip = uiState.descrEquip,
                itemMenuModelList = uiState.menuList,
                setSelection = viewModel::setSelection,
                textButtonReturn = uiState.textButtonReturn,
                flowMenu = uiState.flowMenu,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                setCloseDialog = viewModel::setCloseDialog,
                onNavOS = onNavOS,
                onNavActivityList = onNavActivityList,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.menuList()
            viewModel.descrEquip()
        }
    }
}

@Composable
fun MenuNoteContent(
    descrEquip: String,
    itemMenuModelList: List<ItemMenuModel>,
    setSelection: (Int) -> Unit,
    textButtonReturn: String,
    flowMenu: FlowMenu,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    setCloseDialog: () -> Unit,
    onNavOS: () -> Unit,
    onNavActivityList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_descr_equip,
                descrEquip
            ),
            font = 26,
            padding = 6
        )
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_menu
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(itemMenuModelList) { item ->
                ItemListDesign(
                    text = item.title,
                    setActionItem = {
                        setSelection(item.id)
                    },
                    font = 26
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = textButtonReturn
            )
        }
        BackHandler {}

        if(flagDialog) {
            val text = when(errors) {
                Errors.INVALID -> stringResource(
                    id = R.string.text_selection_option_invalid,
                    failure
                )
                else -> stringResource(
                    id = R.string.text_failure,
                    failure
                )
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            when(flowMenu){
                FlowMenu.WORK -> onNavOS()
                FlowMenu.STOP -> onNavActivityList()
                else -> {}
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemMenuModelList = listOf(
                    ItemMenuModel(
                        id = 1,
                        title = "TRABALHANDO"
                    ),
                    ItemMenuModel(
                        id = 2,
                        title = "PARADO"
                    ),
                ),
                setSelection = {},
                textButtonReturn = "FINALIZAR BOLETIM",
                flowMenu = FlowMenu.INVALID,
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.INVALID,
                setCloseDialog = {},
                onNavOS = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemMenuModelList = listOf(
                    ItemMenuModel(
                        id = 1,
                        title = "TRABALHANDO"
                    ),
                    ItemMenuModel(
                        id = 2,
                        title = "PARADO"
                    ),
                ),
                setSelection = {},
                textButtonReturn = "FINALIZAR BOLETIM",
                flowMenu = FlowMenu.INVALID,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                errors = Errors.EXCEPTION,
                onNavOS = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewSelectionInvalid() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemMenuModelList = listOf(
                    ItemMenuModel(
                        id = 1,
                        title = "TRABALHANDO"
                    ),
                    ItemMenuModel(
                        id = 2,
                        title = "PARADO"
                    ),
                ),
                setSelection = {},
                textButtonReturn = "FINALIZAR BOLETIM",
                flowMenu = FlowMenu.INVALID,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                errors = Errors.INVALID,
                onNavOS = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}