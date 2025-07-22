package br.com.usinasantafe.cmm.presenter.view.configuration.config

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
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
import br.com.usinasantafe.cmm.BuildConfig
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import kotlin.String

const val TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN = "tag_number_text_field_config_screen"
const val TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN = "tag_nro_equip_text_field_config_screen"
const val TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN = "tag_password_text_field_config_screen"

@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel = hiltViewModel(),
    onNavInitialMenu: () -> Unit
) {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.returnDataConfig()
                viewModel.setConfigMain(
                    BuildConfig.VERSION_NAME,
                    BuildConfig.FLAVOR_app
                )
            }

            ConfigContent(
                number = uiState.number,
                onNumberChanged = viewModel::onNumberChanged,
                password = uiState.password,
                onPasswordChanged = viewModel::onPasswordChanged,
                nroEquip = uiState.nroEquip,
                onNroEquipChanged = viewModel::onNroEquipChanged,
                checkMotoMec = uiState.checkMotoMec,
                onCheckMotoMecChanged = viewModel::onCheckMotoMecChanged,
                onSaveAndUpdate = viewModel::onSaveAndUpdate,
                flagProgress = uiState.flagProgress,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                onNavInitialMenu = onNavInitialMenu,
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}

@Composable
fun ConfigContent(
    number: String,
    onNumberChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    nroEquip: String,
    onNroEquipChanged: (String) -> Unit,
    checkMotoMec: Boolean,
    onCheckMotoMecChanged: (Boolean) -> Unit,
    onSaveAndUpdate: () -> Unit,
    currentProgress: Float,
    flagProgress: Boolean,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    onNavInitialMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_nro_aparelho
            )
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 24.sp
            ),
            value = number,
            onValueChange = onNumberChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
        )
        TitleDesign(
            text = stringResource(id = R.string.text_title_nro_equip)
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 24.sp
            ),
            value = nroEquip,
            onValueChange = onNroEquipChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        TitleDesign(
            text = stringResource(id = R.string.text_title_password)
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            textStyle = TextStyle(
                fontSize = 24.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkMotoMec,
                onCheckedChange = onCheckMotoMecChanged
            )
            Text(
                text = "Apontamento de Motomec"
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        )  {
            Button(
                onClick = onNavInitialMenu,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_cancel)
                )
            }
            Button(
                onClick = {
                    keyboardController?.hide()
                    onSaveAndUpdate()
                },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_save)
                )
            }
        }
        if (flagProgress) {
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
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
            Text(
                text = msgProgress,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        BackHandler {}
    }
    if(flagDialog) {
        if(flagFailure){
            val text = when(errors){
                Errors.FIELD_EMPTY -> stringResource(id = R.string.text_field_empty_config)
                Errors.TOKEN -> stringResource(id = R.string.text_recover_token, failure)
                Errors.UPDATE -> stringResource(id = R.string.text_update_failure, failure)
                Errors.EXCEPTION,
                Errors.INVALID -> stringResource(id = R.string.text_failure, failure)
                else -> ""
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        } else {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_config_success),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = onNavInitialMenu,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                nroEquip = "",
                onNroEquipChanged = {},
                checkMotoMec = true,
                onCheckMotoMecChanged = {},
                onSaveAndUpdate = {},
                flagProgress = false,
                currentProgress = 0.0f,
                levelUpdate = null,
                tableUpdate = "",
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                onNavInitialMenu = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "16997417840",
                onNumberChanged = {},
                password = "12345",
                onPasswordChanged = {},
                nroEquip = "310",
                onNroEquipChanged = {},
                checkMotoMec = false,
                onCheckMotoMecChanged = {},
                onSaveAndUpdate = {},
                onNavInitialMenu = {},
                flagProgress = false,
                currentProgress = 0.0f,
                levelUpdate = null,
                tableUpdate = "",
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewShowProgress() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                nroEquip = "",
                onNroEquipChanged = {},
                checkMotoMec = true,
                onCheckMotoMecChanged = {},
                onSaveAndUpdate = {},
                onNavInitialMenu = {},
                flagProgress = true,
                currentProgress = 0.2f,
                levelUpdate = LevelUpdate.RECOVERY,
                tableUpdate = "Colab",
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewShowMsgFieldEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                nroEquip = "",
                onNroEquipChanged = {},
                checkMotoMec = true,
                onCheckMotoMecChanged = {},
                onSaveAndUpdate = {},
                onNavInitialMenu = {},
                flagProgress = false,
                currentProgress = 0.0f,
                levelUpdate = null,
                tableUpdate = "",
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewShowMsgSuccess() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                nroEquip = "",
                onNroEquipChanged = {},
                checkMotoMec = true,
                onCheckMotoMecChanged = {},
                onSaveAndUpdate = {},
                onNavInitialMenu = {},
                flagProgress = false,
                currentProgress = 0.0f,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "",
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}