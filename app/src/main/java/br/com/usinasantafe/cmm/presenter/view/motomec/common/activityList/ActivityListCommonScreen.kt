package br.com.usinasantafe.cmm.presenter.view.motomec.common.activityList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemListDesign
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.utils.UpdateStatusState

@Composable
fun ActivityListCommonScreen(
    viewModel: ActivityListCommonViewModel = hiltViewModel(),
    onNavOS: () -> Unit,
    onNavHourMeter: () -> Unit,
    onNavStopList: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavTranshipment: () -> Unit,
    onNavNozzleList: () -> Unit,
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.activityList()
            }

            ActivityListCommonScreenContent(
                flowApp = uiState.flowApp,
                activityList = uiState.activityList,
                setIdActivity = viewModel::setIdActivity,
                updateDatabase = viewModel::updateDatabase,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavOS = onNavOS,
                onNavHourMeter = onNavHourMeter,
                onNavStopList = onNavStopList,
                onNavMenuNote = onNavMenuNote,
                onNavTranshipment = onNavTranshipment,
                onNavNozzleList = onNavNozzleList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ActivityListCommonScreenContent(
    flowApp: FlowApp,
    activityList: List<Activity>,
    setIdActivity: (Int) -> Unit,
    updateDatabase: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavOS: () -> Unit,
    onNavHourMeter: () -> Unit,
    onNavStopList: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavTranshipment: () -> Unit,
    onNavNozzleList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_activity
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(activityList) { activity ->
                ItemListDesign(
                    text = activity.descrActivity,
                    setActionItem = {
                        setIdActivity(activity.idActivity)
                    },
                    font = 26
                )
            }
        }
        Button(
            onClick = updateDatabase,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_update
                )
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = {
                when(flowApp) {
                    FlowApp.HEADER_INITIAL,
                    FlowApp.NOTE_WORK -> onNavOS()
                    FlowApp.NOTE_STOP -> onNavMenuNote()
                    FlowApp.TRANSHIPMENT -> onNavTranshipment()
                    FlowApp.REEL_FERT -> onNavNozzleList()
                    else -> {}
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_return
                )
            )
        }
        BackHandler {}

        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog)
        }

        if (status.flagProgress) {
            ProgressUpdate(status)
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            when(flowApp){
                FlowApp.HEADER_INITIAL -> onNavHourMeter()
                FlowApp.NOTE_WORK -> onNavMenuNote()
                FlowApp.NOTE_STOP -> onNavStopList()
                else -> {}
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ActivityListCommonPagePreviewWithData() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            ActivityListCommonScreenContent(
                flowApp = FlowApp.HEADER_INITIAL,
                activityList = listOf(
                    Activity(
                        idActivity = 1,
                        codActivity = 1,
                        descrActivity = "ATIVIDADE 1"
                    )
                ),
                setIdActivity = {},
                updateDatabase = {},
                flagAccess = false,
                setCloseDialog = {},
                    status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    currentProgress = 0f,
                    levelUpdate = null,
                    tableUpdate = "",
                    flagDialog = false,
                ),
                onNavOS = {},
                onNavHourMeter = {},
                onNavStopList = {},
                onNavMenuNote = {},
                onNavTranshipment = {},
                onNavNozzleList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityListCommonScreenPagePreviewWithFailureUpdate() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            ActivityListCommonScreenContent(
                flowApp = FlowApp.HEADER_INITIAL,
                activityList = listOf(
                    Activity(
                        idActivity = 1,
                        codActivity = 1,
                        descrActivity = "ATIVIDADE 1"
                    )
                ),
                setIdActivity = {},
                updateDatabase = {},
                flagAccess = false,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagFailure = true,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = false,
                    currentProgress = 0f,
                    levelUpdate = null,
                    tableUpdate = "",
                    flagDialog = true,
                ),
                onNavOS = {},
                onNavHourMeter = {},
                onNavStopList = {},
                onNavMenuNote = {},
                onNavTranshipment = {},
                onNavNozzleList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityListCommonScreenPagePreviewWithProgressUpdate() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            ActivityListCommonScreenContent(
                flowApp = FlowApp.HEADER_INITIAL,
                activityList = listOf(
                    Activity(
                        idActivity = 1,
                        codActivity = 1,
                        descrActivity = "ATIVIDADE 1"
                    )
                ),

                setIdActivity = {},
                updateDatabase = {},
                flagAccess = false,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_activity",
                    currentProgress = 0.3333f,
                    flagDialog = false,
                ),
                onNavOS = {},
                onNavHourMeter = {},
                onNavStopList = {},
                onNavMenuNote = {},
                onNavTranshipment = {},
                onNavNozzleList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityListCommonScreenPagePreviewWithFailureError() {
    CMMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            ActivityListCommonScreenContent(
                flowApp = FlowApp.HEADER_INITIAL,
                activityList = listOf(
                    Activity(
                        idActivity = 1,
                        codActivity = 1,
                        descrActivity = "ATIVIDADE 1"
                    )
                ),

                setIdActivity = {},
                updateDatabase = {},
                flagAccess = false,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = "Failure",
                    flagProgress = false,
                    currentProgress = 0f,
                    levelUpdate = null,
                    tableUpdate = "",
                    flagDialog = true,
                ),
                onNavOS = {},
                onNavHourMeter = {},
                onNavStopList = {},
                onNavMenuNote = {},
                onNavTranshipment = {},
                onNavNozzleList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}