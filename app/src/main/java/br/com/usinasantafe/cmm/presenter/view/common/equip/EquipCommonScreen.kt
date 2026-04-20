package br.com.usinasantafe.cmm.presenter.view.common.equip

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign

@Composable
fun EquipCommonScreen(
    viewModel: EquipCommonViewModel = hiltViewModel(),
    onNavOperator: () -> Unit,
    onNavTurnList: () -> Unit,
    onNavInitialMenuPreCEC: () -> Unit,
    onNavOS: () -> Unit,
    onNavMsgNumberTrailer: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.get()
            }

            EquipCommonContent(
                flowApp = uiState.flowApp,
                checkClass = uiState.checkClass,
                description = uiState.description,
                set = viewModel::set,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavRegOperator = onNavOperator,
                onNavTurnList = onNavTurnList,
                onNavInitialMenuPreCEC = onNavInitialMenuPreCEC,
                onNavOS = onNavOS,
                onNavMsgNumberTrailer = onNavMsgNumberTrailer,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun EquipCommonContent(
    flowApp: FlowApp,
    checkClass: Boolean?,
    description: String,
    set: () -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    onNavRegOperator: () -> Unit,
    onNavTurnList: () -> Unit,
    onNavInitialMenuPreCEC: () -> Unit,
    onNavOS: () -> Unit,
    onNavMsgNumberTrailer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_equip
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = description,
                fontSize = 28.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    if(flowApp == FlowApp.HEADER_INITIAL) onNavRegOperator() else onNavInitialMenuPreCEC()
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(
                        id = R.string.text_pattern_cancel
                    )
                )
            }
            Button(
                onClick = set,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(
                        id = R.string.text_pattern_ok
                    )
                )
            }
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavRegOperator() }
            )
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            if(flowApp == FlowApp.HEADER_INITIAL) {
                onNavTurnList()
            } else {
                if(checkClass!!) onNavOS() else onNavMsgNumberTrailer()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun EquipCommonPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EquipCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                checkClass = true,
                description = "200 - TRATOR",
                set = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "Failure",
                onNavRegOperator = {},
                onNavTurnList = {},
                onNavInitialMenuPreCEC = {},
                onNavOS = {},
                onNavMsgNumberTrailer = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EquipCommonPagePreviewMsgFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EquipCommonContent(
                flowApp = FlowApp.HEADER_INITIAL,
                checkClass = true,
                description = "200 - TRATOR",
                set = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                onNavRegOperator = {},
                onNavTurnList = {},
                onNavInitialMenuPreCEC = {},
                onNavOS = {},
                onNavMsgNumberTrailer = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}