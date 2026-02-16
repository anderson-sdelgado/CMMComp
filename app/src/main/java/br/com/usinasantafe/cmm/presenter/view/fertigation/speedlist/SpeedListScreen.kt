package br.com.usinasantafe.cmm.presenter.view.fertigation.speedlist

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
import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
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
fun SpeedListScreen(
    viewModel: SpeedListViewModel = hiltViewModel(),
    onNavPressureList: () -> Unit,
    onNavMenuNote: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.list()
            }

            SpeedListContent(
                list = uiState.list,
                set = viewModel::set,
                updateDatabase = viewModel::updateDatabase,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavPressureList = onNavPressureList,
                onNavMenuNote = onNavMenuNote,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun SpeedListContent(
    list: List<Pressure>,
    set: (Int) -> Unit,
    updateDatabase: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavPressureList: () -> Unit,
    onNavMenuNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_speed
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(list) { item ->
                ItemListDesign(
                    text = "${item.speed}",
                    setActionItem = {
                        set(item.id)
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
            onClick = onNavPressureList,
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
            onNavMenuNote()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedListPagePreviewEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SpeedListContent(
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
                onNavPressureList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedListPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SpeedListContent(
                list = listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 1.0,
                        speed = 100
                    )
                ),
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
                onNavPressureList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedListPagePreviewWithFailureUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SpeedListContent(
                list = listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 1.0,
                        speed = 100
                    )
                ),
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
                onNavPressureList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedListPagePreviewWithProgressUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SpeedListContent(
                list = listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 1.0,
                        speed = 100
                    )
                ),
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
                onNavPressureList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedListPagePreviewWithFailureError() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SpeedListContent(
                list = listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 1.0,
                        speed = 100
                    )
                ),
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
                onNavPressureList = {},
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}