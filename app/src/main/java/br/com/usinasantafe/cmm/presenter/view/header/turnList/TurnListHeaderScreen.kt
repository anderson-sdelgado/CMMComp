package br.com.usinasantafe.cmm.presenter.view.header.turnList

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
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.ui.theme.AlertDialogProgressDesign
import br.com.usinasantafe.cmm.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.ItemListDesign
import br.com.usinasantafe.cmm.ui.theme.TextButtonDesign
import br.com.usinasantafe.cmm.ui.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.Errors

@Composable
fun TurnListHeaderScreen(
    viewModel: TurnListHeaderViewModel = hiltViewModel(),
    onNavEquip: () -> Unit,
    onNavOS: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TurnListHeaderContent(
                turnList = uiState.turnList,
                setIdTurn = viewModel::setIdTurnHeader,
                updateDatabase = viewModel::updateDatabase,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                currentProgress = uiState.currentProgress,
                msgProgress = uiState.msgProgress,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                onNavEquip = onNavEquip,
                onNavOS = onNavOS,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.turnList()
        }
    }
}

@Composable
fun TurnListHeaderContent(
    turnList: List<Turn>,
    setIdTurn: (Int) -> Unit,
    updateDatabase: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    msgProgress: String,
    currentProgress: Float,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    onNavEquip: () -> Unit,
    onNavOS: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_turn
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(turnList) { turn ->
                ItemListDesign(
                    text = turn.descrTurn,
                    setActionItem = {
                        setIdTurn(turn.idTurn)
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
            onClick = onNavEquip,
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
                msgProgress
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            AlertDialogProgressDesign(
                currentProgress = currentProgress,
                msgProgress = msgProgress
            )
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavOS()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TurnListHeaderPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TurnListHeaderContent(
                turnList = listOf(
                    Turn(
                        idTurn = 1,
                        codTurnEquip = 1,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    ),
                ),
                setIdTurn = {},
                updateDatabase = {},
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0f,
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                onNavEquip = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TurnListHeaderPagePreviewWithFailureUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TurnListHeaderContent(
                turnList = emptyList(),
                setIdTurn = {},
                updateDatabase = {},
                flagFailure = true,
                errors = Errors.UPDATE,
                failure = "Failure",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0f,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                onNavEquip = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TurnListHeaderPagePreviewProgressUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TurnListHeaderContent(
                turnList = emptyList(),
                setIdTurn = {},
                updateDatabase = {},
                flagFailure = false,
                errors = Errors.UPDATE,
                failure = "Failure",
                flagProgress = true,
                msgProgress = "Update",
                currentProgress = 0.3333f,
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                onNavEquip = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TurnListHeaderPagePreviewWithFailureError() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TurnListHeaderContent(
                turnList = emptyList(),
                setIdTurn = {},
                updateDatabase = {},
                flagFailure = true,
                errors = Errors.EXCEPTION,
                failure = "Failure",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0f,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                onNavEquip = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
