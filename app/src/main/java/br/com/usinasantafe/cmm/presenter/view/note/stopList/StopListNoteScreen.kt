package br.com.usinasantafe.cmm.presenter.view.note.stopList

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
                viewModel.stopList()
            }

            StopListNoteContent(
                stopList = uiState.stopList,
                setIdStop = viewModel::setIdStop,
                field = uiState.field,
                onFieldChanged = viewModel::onFieldChanged,
                updateDatabase = viewModel::updateDatabase,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
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
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    currentProgress: Float,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
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

        if (flagDialog) {
            val text = if (flagFailure) {
                when(errors) {
                    Errors.UPDATE -> stringResource(
                        id = R.string.text_update_failure,
                        failure
                    )
                    else -> stringResource(
                        id = R.string.text_failure,
                        failure
                    )
                }
            } else {
                when(levelUpdate){
                    LevelUpdate.RECOVERY -> stringResource(id = R.string.text_msg_recovery, tableUpdate)
                    LevelUpdate.CLEAN -> stringResource(id = R.string.text_msg_clean, tableUpdate)
                    LevelUpdate.SAVE -> stringResource(id = R.string.text_msg_save, tableUpdate)
                    LevelUpdate.GET_TOKEN -> stringResource(id = R.string.text_msg_get_token)
                    LevelUpdate.SAVE_TOKEN -> stringResource(id = R.string.text_msg_save_token)
                    LevelUpdate.FINISH_UPDATE_INITIAL -> stringResource(id = R.string.text_msg_finish_update_initial)
                    LevelUpdate.FINISH_UPDATE_COMPLETED -> stringResource(id = R.string.text_msg_finish_update_completed)
                    null -> failure
                }
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            val msgProgress = when(levelUpdate){
                LevelUpdate.RECOVERY -> stringResource(id = R.string.text_msg_recovery, tableUpdate)
                LevelUpdate.CLEAN -> stringResource(id = R.string.text_msg_clean, tableUpdate)
                LevelUpdate.SAVE -> stringResource(id = R.string.text_msg_save, tableUpdate)
                LevelUpdate.GET_TOKEN -> stringResource(id = R.string.text_msg_get_token)
                LevelUpdate.SAVE_TOKEN -> stringResource(id = R.string.text_msg_save_token)
                LevelUpdate.FINISH_UPDATE_INITIAL -> stringResource(id = R.string.text_msg_finish_update_initial)
                LevelUpdate.FINISH_UPDATE_COMPLETED -> stringResource(id = R.string.text_msg_finish_update_completed)
                null -> failure
            }
            AlertDialogProgressDesign(
                currentProgress = currentProgress,
                msgProgress = msgProgress
            )
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
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
                flagAccess = false,
                flagDialog = false,
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
                flagFailure = true,
                errors = Errors.UPDATE,
                failure = "Failure",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
                flagAccess = false,
                flagDialog = true,
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
                flagFailure = false,
                errors = Errors.UPDATE,
                failure = "Failure",
                flagProgress = true,
                levelUpdate = LevelUpdate.RECOVERY,
                tableUpdate = "tb_stop",
                currentProgress = 0.33333f,
                flagAccess = false,
                flagDialog = false,
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
                flagFailure = true,
                errors = Errors.EXCEPTION,
                failure = "Failure",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
