package br.com.usinasantafe.cmm.presenter.view.mechanic.os

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
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.errors
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressIndeterminateDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign
import br.com.usinasantafe.cmm.utils.UpdateStatusState

@Composable
fun OSMechanicScreen(
    viewModel: OSMechanicViewModel = hiltViewModel(),
    onNavMenuNote: () -> Unit,
    onNavItemList: () -> Unit,
    onNavInputItem: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            OSMechanicContent(
                nroOS = uiState.nroOS,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavMenuNote = onNavMenuNote,
                onNavItemList = onNavItemList,
                onNavInputItem = onNavInputItem,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OSMechanicContent(
    nroOS: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    status: UpdateStatusState,
    onNavMenuNote: () -> Unit,
    onNavItemList: () -> Unit,
    onNavInputItem: () -> Unit,
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
            onNavMenuNote()
        }

        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog)
        }

        if (status.flagProgress) {
            ProgressUpdate(status)
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            if(status.flagFailure){
                onNavInputItem()
            } else {
                onNavItemList()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OSMechanicPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSMechanicContent(
                nroOS = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                    flagDialog = false,
                ),
                onNavMenuNote = {},
                onNavItemList = {},
                onNavInputItem = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSMechanicPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSMechanicContent(
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                    flagDialog = false,
                ),
                onNavMenuNote = {},
                onNavItemList = {},
                onNavInputItem = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSMechanicPagePreviewWithProgress() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSMechanicContent(
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = 0.1f,
                    flagDialog = false
                ),
                onNavMenuNote = {},
                onNavItemList = {},
                onNavInputItem = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSMechanicPagePreviewMsgFieldEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSMechanicContent(
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
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
                onNavMenuNote = {},
                onNavItemList = {},
                onNavInputItem = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSMechanicPagePreviewMsgFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSMechanicContent(
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(),
                onNavMenuNote = {},
                onNavItemList = {},
                onNavInputItem = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSMechanicPagePreviewMsgNonExistent() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSMechanicContent(
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(),
                onNavMenuNote = {},
                onNavItemList = {},
                onNavInputItem = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}