package br.com.usinasantafe.cmm.presenter.view.common.os

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
import br.com.usinasantafe.cmm.ui.theme.AlertDialogProgressIndeterminateDesign
import br.com.usinasantafe.cmm.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.ui.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.TypeButton

@Composable
fun OSCommonScreen(
    viewModel: OSCommonViewModel = hiltViewModel(),
    onNavTurn: () -> Unit,
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            OSCommonContent(
                flowApp = uiState.flowApp,
                nroOS = uiState.nroOS,
                setTextField = viewModel::setTextField,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                onNavTurn = onNavTurn,
                onNavActivityList = onNavActivityList,
                onNavMenuNote = onNavMenuNote,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getNroOS()
        }
    }
}

@Composable
fun OSCommonContent(
    flowApp: FlowApp,
    nroOS: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    flagProgress: Boolean,
    msgProgress: String,
    onNavTurn: () -> Unit,
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_os
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
            value = nroOS,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
            flagUpdate = false
        )
        BackHandler {
            when(flowApp){
                FlowApp.HEADER_INITIAL -> onNavTurn()
                FlowApp.NOTE_WORK -> onNavMenuNote()
                else -> {}
            }
        }

        if(flagDialog) {

            val text = when (errors) {
                Errors.FIELD_EMPTY -> stringResource(
                    id = R.string.text_field_empty,
                    stringResource(id = R.string.text_title_os)
                )
                Errors.UPDATE,
                Errors.TOKEN,
                Errors.EXCEPTION -> stringResource(id = R.string.text_failure, failure)
                Errors.INVALID -> stringResource(
                    id = R.string.text_input_data_non_existent,
                    stringResource(id = R.string.text_title_os)
                )
                else -> ""
            }

            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            AlertDialogProgressIndeterminateDesign(
                msgProgress = msgProgress
            )
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            when(flowApp){
                FlowApp.HEADER_INITIAL,
                FlowApp.NOTE_WORK -> onNavActivityList()
                else -> {}
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OSCommonPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                msgProgress = "",
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSCommonPagePreviewWithProgress() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                flagProgress = true,
                msgProgress = "Checando número da OS",
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSCommonPagePreviewMsgFieldEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                msgProgress = "",
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSCommonPagePreviewMsgFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                flagProgress = false,
                msgProgress = "",
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSCommonPagePreviewNroOSNonExistent() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.INVALID,
                flagProgress = false,
                msgProgress = "",
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}