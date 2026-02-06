package br.com.usinasantafe.cmm.presenter.view.transhipment

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
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign
import br.com.usinasantafe.cmm.utils.UpdateStatusState

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
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
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
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
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
        TextFieldDesign(
            value = nroEquip
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField
        )
        BackHandler {
            if(flowApp == FlowApp.NOTE_WORK) onNavActivityList() else onNavMenuNote()
        }

        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog, value = stringResource(id = R.string.text_title_operator))
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
fun TranshipmentPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TranshipmentContent(
                flowApp = FlowApp.NOTE_WORK,
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
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
                setCloseDialog = {},
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
                status = UpdateStatusState(
                    flagFailure = true,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                    flagDialog = true,
                ),
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
                status = UpdateStatusState(
                    failure = "",
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "equip",
                    flagProgress = true,
                    currentProgress = 0.3333334f,
                    flagDialog = false,
                ),
                onNavMenuNote = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
