package br.com.usinasantafe.cmm.presenter.view.cec.initialMenuPreCEC

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonMaxWidth
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemDefaultListDesign

@Composable
fun InitialMenuPreCECScreen(
    viewModel: InitialMenuPreCECViewModel = hiltViewModel(),
    onNavEquip: () -> Unit,
    onNavHistoricalPreCEC: () -> Unit,
    onNavHistoricalCEC: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            InitialMenuPreCECContent(
                onCheckAccess = viewModel::checkAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                flagAccess = uiState.flagAccess,
                onNavEquip = onNavEquip,
                onNavHistoricalPreCEC = onNavHistoricalPreCEC,
                onNavHistoricalCEC = onNavHistoricalCEC,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun InitialMenuPreCECContent(
    onCheckAccess: () -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    flagAccess: Boolean,
    onNavEquip: () -> Unit,
    onNavHistoricalPreCEC: () -> Unit,
    onNavHistoricalCEC: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_initial_menu_certificate
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemDefaultListDesign(
                    text = stringResource(
                        id = R.string.text_item_initial_pre_cec
                    ),
                    setActionItem = onCheckAccess,
                    font = 26
                )
            }
            item {
                ItemDefaultListDesign(
                    text = stringResource(
                        id = R.string.text_item_historical_pre_cec
                    ),
                    setActionItem = onNavHistoricalPreCEC,
                    font = 26
                )
            }
            item {
                ItemDefaultListDesign(
                    text = stringResource(
                        id = R.string.text_item_historical_cec
                    ),
                    setActionItem = onNavHistoricalCEC,
                    font = 26
                )
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        ButtonMaxWidth(R.string.text_pattern_return) {  }
        BackHandler {}

        if (flagDialog) {
            val text =
                if (failure.isEmpty()) {
                    stringResource(id = R.string.text_blocked_access_certificated)
                } else {
                    stringResource(
                        id = R.string.text_failure,
                        failure
                    )
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
fun InitialMenuPreCECPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuPreCECContent(
                onCheckAccess = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                flagAccess = false,
                onNavEquip = {},
                onNavHistoricalPreCEC = {},
                onNavHistoricalCEC = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialMenuPreCECPagePreviewException() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuPreCECContent(
                onCheckAccess = {},
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure",
                flagAccess = false,
                onNavEquip = {},
                onNavHistoricalPreCEC = {},
                onNavHistoricalCEC = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialMenuPreCECPagePreviewBlocked() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuPreCECContent(
                onCheckAccess = {},
                flagDialog = true,
                setCloseDialog = {},
                failure = "",
                flagAccess = false,
                onNavEquip = {},
                onNavHistoricalPreCEC = {},
                onNavHistoricalCEC = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}