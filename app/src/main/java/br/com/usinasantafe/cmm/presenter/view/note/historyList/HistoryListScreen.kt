package br.com.usinasantafe.cmm.presenter.view.note.historyList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemHistoryListDesign
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.utils.FlowNote

@Composable
fun HistoryListScreen(
    viewModel: HistoryListViewModel = hiltViewModel(),
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {

            }

            HistoryListContent(
                itemList = listOf(),
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HistoryListContent(
    itemList: List<ItemHistoryScreenModel>,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_history
            )
        )
        if(itemList.isEmpty()){
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.text_msg_no_data_history
                    ),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
            ) {
                items(itemList) { item ->
                    ItemHistoryListDesign(
                        type = item.type,
                        descr = item.descr,
                        dateHour = item.dateHour,
                        detail = item.detail,
                        setActionItem = {},
                        font = 26
                    )
                }
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_return
                )
            )
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(
                    id = R.string.text_failure,
                    failure
                ),
                setCloseDialog = setCloseDialog
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HistoryListPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HistoryListContent(
                itemList = listOf(
                    ItemHistoryScreenModel(
                        type = FlowNote.WORK,
                        descr = "ATIVIDADE: TRANPORTE DE CANA",
                        dateHour = "21/10/2025 15:23",
                        detail = ""
                    ),
                    ItemHistoryScreenModel(
                        type = FlowNote.STOP,
                        descr = "PARADA: CHUVA",
                        dateHour = "21/10/2025 15:23",
                        detail = "Teste\nTeste"
                    )
                ),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryListPagePreviewNoData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HistoryListContent(
                itemList = listOf(),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryListPagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HistoryListContent(
                itemList = listOf(),
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}