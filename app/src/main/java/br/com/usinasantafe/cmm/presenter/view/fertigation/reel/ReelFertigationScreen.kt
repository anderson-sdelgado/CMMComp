package br.com.usinasantafe.cmm.presenter.view.fertigation.reel

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
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.Progress
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign
import br.com.usinasantafe.cmm.utils.UpdateStatusState

@Composable
fun ReelFertigationScreen(
    viewModel: ReelFertigationViewModel = hiltViewModel(),
    onNavReelList: () -> Unit,
    onNavActivityList: () -> Unit,
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ReelFertigationContent(
                nroEquip = uiState.nroEquip,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavReelList = onNavReelList,
                onNavActivityList = onNavActivityList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ReelFertigationContent(
    nroEquip: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavReelList: () -> Unit,
    onNavActivityList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_reel
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
            onNavReelList()
        }

        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog, value = stringResource(id = R.string.text_title_operator))
        }

        if (status.flagProgress) {
            Progress(status)
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
fun ReelFertigationPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ReelFertigationContent(
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = true,
                setCloseDialog = {},
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
                onNavReelList = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReelFertigationPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ReelFertigationContent(
                nroEquip = "530",
                setTextField = { _, _ -> },
                flagAccess = true,
                setCloseDialog = {},
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
                onNavReelList = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReelFertigationPagePreviewWithMsgEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ReelFertigationContent(
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = true,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0f,
                ),
                onNavReelList = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReelFertigationPagePreviewWithUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ReelFertigationContent(
                nroEquip = "",
                setTextField = { _, _ -> },
                flagAccess = true,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagDialog = false,
                    failure = "",
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_equip",
                    flagProgress = true,
                    currentProgress = 0.3333334f,
                ),
                onNavReelList = {},
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}