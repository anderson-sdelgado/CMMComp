package br.com.usinasantafe.cmm.presenter.view.motomec.note.stopList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemListDesign
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.utils.UpdateStatusState

const val TAG_FILTER_TEXT_FIELD_STOP_LIST_SCREEN = "tag_filter_text_field_stop_list_screen"

@Composable
fun StopListNoteScreen(
    viewModel: StopListNoteViewModel = hiltViewModel(),
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.list()
            }

            StopListNoteContent(
                stopList = uiState.list,
                setIdStop = viewModel::setIdStop,
                field = uiState.field,
                onFieldChanged = viewModel::onFieldChanged,
                updateDatabase = viewModel::updateDatabase,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavActivityList = onNavActivityList,
                onNavMenuNote = onNavMenuNote,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun StopListNoteContent(
    stopList: List<StopScreenModel>,
    setIdStop: (Int) -> Unit,
    field: String,
    onFieldChanged: (String) -> Unit,
    updateDatabase: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            ),
            value = field,
            onValueChange = onFieldChanged,
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.text_placeholder_field
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_FILTER_TEXT_FIELD_STOP_LIST_SCREEN)
        )
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_stop
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(stopList) {
                ItemListDesign(
                    text = it.descr,
                    setActionItem = {
                        setIdStop(it.id)
                    },
                    id = it.id,
                    padding = 6
                )
            }
        }
        Button(
            onClick = updateDatabase,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_update
                )
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = onNavActivityList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog)
        }

        if (status.flagProgress) {
            ProgressUpdate(status)
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavMenuNote()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StopListNotePagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            StopListNoteContent(
                stopList = listOf(
                    StopScreenModel(
                        id = 1,
                        descr = "10 - PARADA 1"
                    ),
                    StopScreenModel(
                        id = 2,
                        descr = "20 - PARADA 2"
                    )
                ),
                setIdStop = {},
                field = "",
                onFieldChanged = {},
                updateDatabase = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                    flagDialog = false,
                ),
                setCloseDialog = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StopListNoteScreenPagePreviewWithFailureUpdate() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            StopListNoteContent(
                stopList = listOf(
                    StopScreenModel(
                        id = 1,
                        descr = "10 - PARADA 1"
                    ),
                    StopScreenModel(
                        id = 2,
                        descr = "20 - PARADA 2"
                    )
                ),
                setIdStop = {},
                field = "",
                onFieldChanged = {},
                updateDatabase = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = true,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                    flagDialog = true,
                ),
                setCloseDialog = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StopListNoteScreenPagePreviewWithProgressUpdate() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            StopListNoteContent(
                stopList = listOf(
                    StopScreenModel(
                        id = 1,
                        descr = "10 - PARADA 1"
                    ),
                    StopScreenModel(
                        id = 2,
                        descr = "20 - PARADA 2"
                    )
                ),
                setIdStop = {},
                field = "",
                onFieldChanged = {},
                updateDatabase = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_stop",
                    currentProgress = 0.33333f,
                    flagDialog = false,
                ),
                setCloseDialog = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StopListNoteScreenPagePreviewWithFailureError() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            StopListNoteContent(
                stopList = listOf(
                    StopScreenModel(
                        id = 1,
                        descr = "10 - PARADA 1"
                    ),
                    StopScreenModel(
                        id = 2,
                        descr = "20 - PARADA 2"
                    )
                ),
                setIdStop = {},
                field = "",
                onFieldChanged = {},
                updateDatabase = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = "Failure",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                    flagDialog = true,
                ),
                setCloseDialog = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
