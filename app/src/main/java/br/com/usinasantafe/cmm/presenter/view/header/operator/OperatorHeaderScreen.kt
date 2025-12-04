package br.com.usinasantafe.cmm.presenter.view.header.operator

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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton

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
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                currentProgress = uiState.currentProgress,
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
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    currentProgress: Float,
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
            value = regColab,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField
        )
        BackHandler {
            onNavInitialMenu()
        }

        if (flagDialog) {
            val text = if (flagFailure) {
                when (errors) {
                    Errors.FIELD_EMPTY -> stringResource(
                        id = R.string.text_field_empty,
                        stringResource(id = R.string.text_title_operator)
                    )
                    Errors.UPDATE -> stringResource(
                        id = R.string.text_update_failure,
                        failure
                    )
                    Errors.INVALID -> stringResource(
                        id = R.string.text_input_data_invalid,
                        stringResource(
                            id = R.string.text_title_operator
                        )
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
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
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
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
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
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0f,
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
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                levelUpdate = LevelUpdate.RECOVERY,
                tableUpdate = "colab",
                flagProgress = true,
                currentProgress = 0.3333334f,
                onNavInitialMenu = {},
                onNavEquip = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}