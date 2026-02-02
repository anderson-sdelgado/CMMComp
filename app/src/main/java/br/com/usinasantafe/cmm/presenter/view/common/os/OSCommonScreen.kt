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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressIndeterminateDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.errors
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign

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

            LaunchedEffect(Unit) {
                viewModel.getNroOS()
            }

            OSCommonContent(
                flowApp = uiState.flowApp,
                nroOS = uiState.nroOS,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
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
        TextFieldDesign(
            value = nroOS
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
            val value = stringResource(id = R.string.text_title_os)
            val text = errors(errors, failure, value)
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) AlertDialogProgressIndeterminateDesign(msgProgress)

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