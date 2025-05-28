package br.com.usinasantafe.cmm.presenter.header.measure

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
import br.com.usinasantafe.cmm.utils.TypeButton

@Composable
fun MeasureScreen(
    viewModel: MeasureViewModel = hiltViewModel(),
    onNavActivityList: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MeasureContent(
                measure = uiState.measure,
                measureOld = uiState.measureOld,
                setTextField = viewModel::setTextField,
                setCloseDialog = viewModel::setCloseDialog,
                setMeasure = viewModel::setMeasureInitialHeader,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavActivityList = onNavActivityList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MeasureContent(
    measure: String,
    measureOld: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    setMeasure: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavActivityList: () -> Unit,
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
            value = measure,
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
            onNavActivityList()
        }

        if(flagDialog) {

            val text = when (errors) {
                Errors.FIELDEMPTY -> stringResource(
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
                    measure,
                    measureOld
                )
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeasurePagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MeasureContent(
                measure = "0,0",
                measureOld = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELDEMPTY,
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeasurePagePreviewEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MeasureContent(
                measure = "0,0",
                measureOld = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.FIELDEMPTY,
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeasurePagePreviewInvalid() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MeasureContent(
                measure = "10000,0",
                measureOld = "20000,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.INVALID,
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeasurePagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MeasureContent(
                measure = "10000,0",
                measureOld = "20000,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                setMeasure = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavActivityList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}