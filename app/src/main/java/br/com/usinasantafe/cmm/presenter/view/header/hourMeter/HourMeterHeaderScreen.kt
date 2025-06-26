package br.com.usinasantafe.cmm.presenter.view.header.hourMeter

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
import br.com.usinasantafe.cmm.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.cmm.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.ui.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.TypeButton

@Composable
fun HourMeterHeaderScreen(
    viewModel: HourMeterHeaderViewModel = hiltViewModel(),
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavInitialMenu: () -> Unit,
    onNavQuestionUpdateCheckList: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HourMeterHeaderContent(
                flowApp = uiState.flowApp,
                hourMeter = uiState.hourMeter,
                hourMeterOld = uiState.hourMeterOld,
                setTextField = viewModel::setTextField,
                setCloseDialog = viewModel::setCloseDialog,
                setMeasure = viewModel::setHourMeterHeader,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavActivityList = onNavActivityList,
                onNavMenuNote = onNavMenuNote,
                onNavInitialMenu = onNavInitialMenu,
                onNavQuestionUpdateCheckList = onNavQuestionUpdateCheckList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HourMeterHeaderContent(
    flowApp: FlowApp,
    hourMeter: String,
    hourMeterOld: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    setMeasure: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavInitialMenu: () -> Unit,
    onNavQuestionUpdateCheckList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_measure
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
            value = hourMeter,
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
                FlowApp.HEADER_INITIAL -> onNavActivityList()
                FlowApp.HEADER_FINISH -> onNavMenuNote()
                else -> {}
            }

        }

        if(flagDialog) {

            val text = when (errors) {
                Errors.FIELD_EMPTY -> stringResource(
                    id = R.string.text_field_empty,
                    stringResource(id = R.string.text_title_measure)
                )
                Errors.UPDATE,
                Errors.TOKEN,
                Errors.EXCEPTION -> stringResource(
                    id = R.string.text_failure,
                    failure
                )
                Errors.INVALID -> stringResource(
                    id = R.string.text_input_measure_invalid,
                    hourMeter,
                    hourMeterOld
                )
                else -> ""
            }

            if(errors != Errors.INVALID) {
                AlertDialogSimpleDesign(
                    text = text,
                    setCloseDialog = setCloseDialog,
                )
            } else {
                AlertDialogCheckDesign(
                    text = text,
                    setCloseDialog = setCloseDialog,
                    setActionButtonOK = setMeasure
                )
            }
        }

        LaunchedEffect(flagAccess) {
            if(flagAccess) {
                when(flowApp){
                    FlowApp.HEADER_INITIAL -> onNavMenuNote()
                    FlowApp.HEADER_FINISH -> onNavInitialMenu()
                    FlowApp.CHECK_LIST -> onNavQuestionUpdateCheckList()
                    else -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourMeterHeaderPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HourMeterHeaderContent(
                flowApp = FlowApp.HEADER_INITIAL,
                hourMeter = "0,0",
                hourMeterOld = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourMeterHeaderPagePreviewEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HourMeterHeaderContent(
                flowApp = FlowApp.HEADER_INITIAL,
                hourMeter = "0,0",
                hourMeterOld = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourMeterHeaderPagePreviewInvalid() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HourMeterHeaderContent(
                flowApp = FlowApp.HEADER_INITIAL,
                hourMeter = "10000,0",
                hourMeterOld = "20000,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.INVALID,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourMeterHeaderPagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HourMeterHeaderContent(
                flowApp = FlowApp.HEADER_INITIAL,
                hourMeter = "10000,0",
                hourMeterOld = "20000,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}