package br.com.usinasantafe.cmm.presenter.view.common.os

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import br.com.usinasantafe.cmm.lib.App
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressIndeterminateDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.errors
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.Progress
import br.com.usinasantafe.cmm.presenter.theme.ProgressIndeterminate
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign
import br.com.usinasantafe.cmm.utils.UpdateStatusState

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
                viewModel.start()
            }

            OSCommonContent(
                flowApp = uiState.flowApp,
                app = uiState.app,
                nroOS = uiState.nroOS,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
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
    app: App,
    nroOS: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
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
            flagUpdate = (app == App.ECM)
        )
        BackHandler {
            when(flowApp){
                FlowApp.HEADER_INITIAL -> onNavTurn()
                FlowApp.NOTE_WORK -> onNavMenuNote()
                else -> {}
            }
        }

        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog, value = stringResource(id = R.string.text_title_os))
        }

        if (status.flagProgress) {
            if(app == App.ECM) ProgressIndeterminate(status) else Progress(status)
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavActivityList()
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
                app = App.PMM,
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
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
fun OSCommonPagePreviewECM() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                app = App.ECM,
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
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
                app = App.PMM,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_OS,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
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
                app = App.PMM,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
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
                app = App.PMM,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = "Failure",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
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
fun OSCommonPagePreviewMsgNonExistent() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                app = App.PMM,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.INVALID,
                    failure = "Failure",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
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
fun OSCommonPagePreviewUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                app = App.ECM,
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    failure = "",
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    flagProgress = true,
                    currentProgress = 0.3333334f,
                ),
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}