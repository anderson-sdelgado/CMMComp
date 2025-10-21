package br.com.usinasantafe.cmm.presenter.view.note.menu

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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemListDesign
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowNote

@Composable
fun MenuNoteScreen(
    viewModel: MenuNoteViewModel = hiltViewModel(),
    onNavOS: () -> Unit,
    onNavActivityList: () -> Unit,
    onNavMeasure: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.menuList()
                viewModel.descrEquip()
            }

            MenuNoteContent(
                descrEquip = uiState.descrEquip,
                itemMenuModelList = uiState.menuList,
                setSelection = viewModel::setSelection,
                textButtonReturn = uiState.textButtonReturn,
                onButtonReturn = viewModel::onButtonReturn,
                flowNote = uiState.flowNote,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                setCloseDialog = viewModel::setCloseDialog,
                onNavOS = onNavOS,
                onNavActivityList = onNavActivityList,
                onNavMeasure = onNavMeasure,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MenuNoteContent(
    descrEquip: String,
    itemMenuModelList: List<ItemMenuModel>,
    setSelection: (Int) -> Unit,
    textButtonReturn: String,
    onButtonReturn: () -> Unit,
    flowNote: FlowNote,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    setCloseDialog: () -> Unit,
    onNavOS: () -> Unit,
    onNavActivityList: () -> Unit,
    onNavMeasure: () -> Unit,
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
            onClick = onButtonReturn,
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
                Errors.HEADER_EMPTY -> stringResource(id = R.string.text_header_empty)
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
            when(flowNote){
                FlowNote.WORK -> onNavOS()
                FlowNote.STOP -> onNavActivityList()
                FlowNote.FINISH -> onNavMeasure()
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
                onButtonReturn = {},
                flowNote = FlowNote.INVALID,
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.INVALID,
                setCloseDialog = {},
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
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
                onButtonReturn = {},
                flowNote = FlowNote.INVALID,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                errors = Errors.EXCEPTION,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
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
                onButtonReturn = {},
                flowNote = FlowNote.INVALID,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                errors = Errors.INVALID,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewHeaderEmpty() {
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
                onButtonReturn = {},
                flowNote = FlowNote.INVALID,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                errors = Errors.HEADER_EMPTY,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}