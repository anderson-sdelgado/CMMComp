package br.com.usinasantafe.cmm.presenter.view.cec.release

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
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.MsgErrors
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign

@Composable
fun ReleaseScreen(
    viewModel: ReleaseViewModel = hiltViewModel(),
    onNavActivityList: () -> Unit,
    onNavMsgTrailer: () -> Unit,
    onNavCheckPreCEC: () -> Unit,
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ReleaseContent(
                idRelease = uiState.idRelease,
                flagEnd = uiState.flagEnd,
                setTextField = viewModel::setTextField,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavActivityList = onNavActivityList,
                onNavMsgTrailer = onNavMsgTrailer,
                onNavCheckPreCEC = onNavCheckPreCEC,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ReleaseContent(
    idRelease: String,
    flagEnd: Boolean,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavActivityList: () -> Unit,
    onNavMsgTrailer: () -> Unit,
    onNavCheckPreCEC: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_release
            )
        )
        TextFieldDesign(idRelease)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
            flagUpdate = false
        )
        BackHandler {
            onNavActivityList()
        }

        if(flagDialog) {
            MsgErrors(errors = errors, setCloseDialog = setCloseDialog, failure = failure)
        }

        LaunchedEffect(flagAccess) {
            if(flagAccess) {
                if(flagEnd) onNavCheckPreCEC() else onNavMsgTrailer()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ReleasePagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ReleaseContent(
                idRelease = "123456",
                flagEnd = true,
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavActivityList = {},
                onNavMsgTrailer = {},
                onNavCheckPreCEC = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}