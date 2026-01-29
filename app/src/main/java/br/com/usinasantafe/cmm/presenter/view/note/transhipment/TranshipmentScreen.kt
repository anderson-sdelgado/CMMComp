package br.com.usinasantafe.cmm.presenter.view.note.transhipment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.msg
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme

@Composable
fun TranshipmentScreen(
    viewModel: TranshipmentViewModel = hiltViewModel(),
    onNavMenuNote: () -> Unit,
    onNavActivityList: () -> Unit,
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TranshipmentContent(
                flowApp = uiState.flowApp,
                nroEquip = uiState.nroEquip,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.status.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.status.flagFailure,
                errors = uiState.status.errors,
                failure = uiState.status.failure,
                flagProgress = uiState.status.flagProgress,
                levelUpdate = uiState.status.levelUpdate,
                tableUpdate = uiState.status.tableUpdate,
                currentProgress = uiState.status.currentProgress,
                onNavMenuNote = onNavMenuNote,
                onNavActivityList = onNavActivityList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TranshipmentContent(
    flowApp: FlowApp,
    nroEquip: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    currentProgress: Float,
    onNavMenuNote: () -> Unit,
    onNavActivityList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_transhipment
            )
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp,
            ),
            readOnly = true,
            value = nroEquip,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField
        )
        BackHandler {
            if(flowApp == FlowApp.NOTE_WORK) onNavActivityList() else onNavMenuNote()
        }

        if (flagDialog) {
            val text = if (flagFailure) {
                when (errors) {
                    Errors.FIELD_EMPTY -> stringResource(
                        id = R.string.text_field_empty,
                        stringResource(id = R.string.text_title_transhipment)
                    )
                    Errors.UPDATE -> stringResource(
                        id = R.string.text_update_failure,
                        failure
                    )
                    Errors.INVALID -> stringResource(
                        id = R.string.text_input_data_invalid,
                        stringResource(
                            id = R.string.text_title_transhipment
                        )
                    )
                    else -> stringResource(
                        id = R.string.text_failure,
                        failure
                    )
                }
            } else {
                msg(levelUpdate, failure, tableUpdate)
            }

            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            val msgProgress = msg(levelUpdate, failure, tableUpdate)
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
fun TranshipmentPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TranshipmentContent(
                flowApp = FlowApp.NOTE_WORK,
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
                onNavMenuNote = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranshipmentPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TranshipmentContent(
                flowApp = FlowApp.NOTE_WORK,
                nroEquip = "2200",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
                onNavMenuNote = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranshipmentPagePreviewMsgEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TranshipmentContent(
                flowApp = FlowApp.NOTE_WORK,
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                flagFailure = true,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
                onNavMenuNote = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranshipmentPagePreviewUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TranshipmentContent(
                flowApp = FlowApp.NOTE_WORK,
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                levelUpdate = LevelUpdate.RECOVERY,
                tableUpdate = "equip",
                flagProgress = true,
                currentProgress = 0.3333334f,
                onNavMenuNote = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
