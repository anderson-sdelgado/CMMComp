package br.com.usinasantafe.cmm.presenter.view.fertigation.pressureList

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
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemListDesign
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.utils.UpdateStatusState

@Composable
fun PressureListScreen(
    viewModel: PressureListViewModel = hiltViewModel(),
    onNavNozzleList: () -> Unit,
    onNavSpeedList: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.list()
            }

            PressureListContent(
                list = uiState.list,
                set = viewModel::set,
                updateDatabase = viewModel::updateDatabase,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavNozzleList = onNavNozzleList,
                onNavSpeedList = onNavSpeedList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PressureListContent(
    list: List<Double>,
    set: (Double) -> Unit,
    updateDatabase: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavNozzleList: () -> Unit,
    onNavSpeedList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_pressure
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(list) { item ->
                ItemListDesign(
                    text = "$item",
                    setActionItem = {
                        set(item)
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
            onClick = onNavNozzleList,
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
        if (flagAccess) {
            onNavSpeedList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressureListPagePreviewEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PressureListContent(
                list = emptyList(),
                set = {},
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
                onNavNozzleList = {},
                onNavSpeedList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressureListPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PressureListContent(
                list = listOf(1.0),
                set = {},
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
                onNavNozzleList = {},
                onNavSpeedList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressureListPagePreviewWithFailureUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PressureListContent(
                list = listOf(1.0),
                set = {},
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
                onNavNozzleList = {},
                onNavSpeedList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressureListPagePreviewWithProgressUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PressureListContent(
                list = listOf(1.0),
                set = {},
                updateDatabase = {},
                flagAccess = false,
                setCloseDialog = {},
                status = UpdateStatusState(
                    flagFailure = false,
                    errors = Errors.UPDATE,
                    failure = "Failure",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_pressure",
                    currentProgress = 0.3333f,
                    flagDialog = false,
                ),
                onNavNozzleList = {},
                onNavSpeedList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressureListPagePreviewWithFailureError() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PressureListContent(
                list = listOf(1.0),
                set = {},
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
                onNavNozzleList = {},
                onNavSpeedList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}