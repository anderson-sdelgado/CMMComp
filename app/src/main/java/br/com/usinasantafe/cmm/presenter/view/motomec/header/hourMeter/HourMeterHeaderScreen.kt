package br.com.usinasantafe.cmm.presenter.view.motomec.header.hourMeter

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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogCheckDesign
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
fun HourMeterHeaderScreen(
    viewModel: HourMeterHeaderViewModel = hiltViewModel(),
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavInitialMenu: () -> Unit,
    onNavQuestionUpdateCheckList: () -> Unit,
    onNavMotorPump: () -> Unit,
    onNavListPerformance: () -> Unit
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
                set = viewModel::set,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavActivityList = onNavActivityList,
                onNavMenuNote = onNavMenuNote,
                onNavInitialMenu = onNavInitialMenu,
                onNavQuestionUpdateCheckList = onNavQuestionUpdateCheckList,
                onNavMotorPump = onNavMotorPump,
                onNavListPerformance = onNavListPerformance,
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
    set: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavActivityList: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavInitialMenu: () -> Unit,
    onNavQuestionUpdateCheckList: () -> Unit,
    onNavMotorPump: () -> Unit,
    onNavListPerformance: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_hour_meter
            )
        )
        TextFieldDesign(
            value = hourMeter
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
                FlowApp.REEL_FERT -> onNavMotorPump()
                else -> {}
            }

        }

        if(flagDialog) {
            val text = errors(errors, failure, stringResource(id = R.string.text_title_hour_meter), hourMeter, hourMeterOld)
            if(errors != Errors.INVALID) {
                AlertDialogSimpleDesign(text = text, setCloseDialog = setCloseDialog,)
            } else {
                AlertDialogCheckDesign(text = text, setCloseDialog = setCloseDialog, setActionButtonYes = set)
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
                set = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                onNavMotorPump = {},
                onNavListPerformance = {},
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
                set = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                onNavMotorPump = {},
                onNavListPerformance = {},
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
                set = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.INVALID,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                onNavMotorPump = {},
                onNavListPerformance = {},
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
                set = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                onNavMotorPump = {},
                onNavListPerformance = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}