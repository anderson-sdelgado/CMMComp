package br.com.usinasantafe.cmm.presenter.view.header.operator

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
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign
import br.com.usinasantafe.cmm.utils.UpdateStatusState

@Composable
fun OperatorHeaderScreen(
    viewModel: OperatorHeaderViewModel = hiltViewModel(),
    onNavInitialMenu: () -> Unit,
    onNavEquip: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            OperatorHeaderContent(
                regColab = uiState.regColab,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavInitialMenu = onNavInitialMenu,
                onNavEquip = onNavEquip,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OperatorHeaderContent(
    regColab: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavInitialMenu: () -> Unit,
    onNavEquip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_operator
            )
        )
        TextFieldDesign(
            value = regColab
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField
        )
        BackHandler {
            onNavInitialMenu()
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
            onNavEquip()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorHeaderPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OperatorHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                flagAccess = false,
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
                onNavInitialMenu = {},
                onNavEquip = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorHeaderPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OperatorHeaderContent(
                regColab = "19759",
                setTextField = { _, _ -> },
                flagAccess = false,
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
                onNavInitialMenu = {},
                onNavEquip = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorHeaderPagePreviewWithMsgEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OperatorHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                flagAccess = false,
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
                onNavInitialMenu = {},
                onNavEquip = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorHeaderPagePreviewUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OperatorHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagDialog = false,
                    failure = "",
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "colab",
                    flagProgress = true,
                    currentProgress = 0.3333334f,
                ),
                onNavInitialMenu = {},
                onNavEquip = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}