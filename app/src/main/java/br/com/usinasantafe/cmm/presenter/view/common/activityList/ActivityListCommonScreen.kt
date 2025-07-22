package br.com.usinasantafe.cmm.presenter.view.common.activityList

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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemListDesign
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.LevelUpdate

@Composable
fun ActivityListCommonScreen(
    viewModel: ActivityListCommonViewModel = hiltViewModel(),
    onNavOS: () -> Unit,
    onNavMeasure: () -> Unit,
    onNavStopList: () -> Unit,
    onMenuNote: () -> Unit
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
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                onNavOS = onNavOS,
                onNavMeasure = onNavMeasure,
                onNavStopList = onNavStopList,
                onMenuNote = onMenuNote,
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
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    currentProgress: Float,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    onNavOS: () -> Unit,
    onNavMeasure: () -> Unit,
    onNavStopList: () -> Unit,
    onMenuNote: () -> Unit,
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
                    FlowApp.NOTE_STOP -> onMenuNote()
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

        if (flagDialog) {
            val text = if (flagFailure) {
                when(errors) {
                    Errors.UPDATE -> stringResource(
                        id = R.string.text_update_failure,
                        failure
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

        if (flagProgress) {
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
            AlertDialogProgressDesign(
                currentProgress = currentProgress,
                msgProgress = msgProgress
            )
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            when(flowApp){
                FlowApp.HEADER_INITIAL -> onNavMeasure()
                FlowApp.NOTE_WORK -> onMenuNote()
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
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                currentProgress = 0f,
                levelUpdate = null,
                tableUpdate = "",
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                onNavOS = {},
                onNavMeasure = {},
                onNavStopList = {},
                onMenuNote = {},
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
                flagFailure = true,
                errors = Errors.UPDATE,
                failure = "Failure",
                flagProgress = false,
                currentProgress = 0f,
                levelUpdate = null,
                tableUpdate = "",
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                onNavOS = {},
                onNavMeasure = {},
                onNavStopList = {},
                onMenuNote = {},
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
                flagFailure = false,
                errors = Errors.UPDATE,
                failure = "Failure",
                flagProgress = true,
                levelUpdate = LevelUpdate.RECOVERY,
                tableUpdate = "tb_activity",
                currentProgress = 0.3333f,
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                onNavOS = {},
                onNavMeasure = {},
                onNavStopList = {},
                onMenuNote = {},
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
                flagFailure = true,
                errors = Errors.EXCEPTION,
                failure = "Failure",
                flagProgress = false,
                currentProgress = 0f,
                levelUpdate = null,
                tableUpdate = "",
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                onNavOS = {},
                onNavMeasure = {},
                onNavStopList = {},
                onMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}