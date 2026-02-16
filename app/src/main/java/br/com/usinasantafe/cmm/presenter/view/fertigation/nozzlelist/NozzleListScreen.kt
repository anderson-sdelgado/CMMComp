package br.com.usinasantafe.cmm.presenter.view.fertigation.nozzlelist

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
import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
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
fun NozzleListScreen(
    viewModel: NozzleListViewModel = hiltViewModel(),
    onNavActivityList: () -> Unit,
    onNavPressureList: () -> Unit

) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.list()
            }

            NozzleListContent(
                list = uiState.list,
                set = viewModel::set,
                updateDatabase = viewModel::updateDatabase,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                status = uiState.status,
                onNavActivityList = onNavActivityList,
                onNavPressureList = onNavPressureList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NozzleListContent(
    list: List<Nozzle>,
    set: (Int) -> Unit,
    updateDatabase: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    status: UpdateStatusState,
    onNavActivityList: () -> Unit,
    onNavPressureList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_nozzle
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(list) { item ->
                ItemListDesign(
                    text = "${item.cod} - ${item.descr}",
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
            onClick = onNavActivityList,
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
            onNavPressureList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NozzleListPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NozzleListContent(
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
                onNavActivityList = {},
                onNavPressureList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NozzleListPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NozzleListContent(
                list = listOf(
                    Nozzle(
                        id = 1,
                        cod = 1,
                        descr = "Item"
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
                onNavActivityList = {},
                onNavPressureList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NozzleListPagePreviewWithFailureUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NozzleListContent(
                list = listOf(
                    Nozzle(
                        id = 1,
                        cod = 1,
                        descr = "Item"
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
                onNavActivityList = {},
                onNavPressureList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NozzleListPagePreviewWithProgressUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NozzleListContent(
                list = listOf(
                    Nozzle(
                        id = 1,
                        cod = 1,
                        descr = "Item"
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
                    tableUpdate = "tb_nozzle",
                    currentProgress = 0.3333f,
                    flagDialog = false,
                ),
                onNavActivityList = {},
                onNavPressureList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NozzleListPagePreviewWithFailureError() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NozzleListContent(
                list = listOf(
                    Nozzle(
                        id = 1,
                        cod = 1,
                        descr = "Item"
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
                onNavActivityList = {},
                onNavPressureList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}