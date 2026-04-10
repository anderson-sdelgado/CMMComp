package br.com.usinasantafe.cmm.presenter.view.fertigation.collectionList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.presenter.theme.ButtonMaxWidth
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemValueOSListDesign
import br.com.usinasantafe.cmm.presenter.theme.MsgErrors

@Composable
fun CollectionListFertigationScreen(
    viewModel: CollectionListFertigationViewModel = hiltViewModel(),
    onNavMenuNote: () -> Unit,
    onNavCollection: (id: Int) -> Unit,
    onNavSplash: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.list()
            }

            CollectionListFertigationContent(
                list = uiState.list,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavMenuNote = onNavMenuNote,
                onNavCollection = onNavCollection,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun CollectionListFertigationContent(
    list: List<ItemValueOSScreenModel>,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavMenuNote: () -> Unit,
    onNavCollection: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_collection_list
            )
        )
        if (list.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.text_msg_no_data_collection
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
                items(list) { item ->
                    ItemValueOSListDesign(
                        nroOS = item.nroOS,
                        value = item.value,
                        setActionItem = { onNavCollection(item.id) },
                        font = 26
                    )
                }
            }
        }
        ButtonMaxWidth(R.string.text_pattern_return) { onNavMenuNote() }
        BackHandler {}

        if (flagDialog) {
            MsgErrors(errors, setCloseDialog, failure)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CollectionListFertigationPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionListFertigationContent(
                list = emptyList(),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavMenuNote = {},
                onNavCollection = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionListFertigationPagePreviewWithData() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionListFertigationContent(
                list = listOf(
                    ItemValueOSScreenModel(
                        id = 1,
                        nroOS = 123456,
                        value = null
                    ),
                    ItemValueOSScreenModel(
                        id = 2,
                        nroOS = 456123,
                        value = 10.0
                    )
                ),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavMenuNote = {},
                onNavCollection = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionListFertigationPagePreviewWithDataAndButtonFinish() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionListFertigationContent(
                list = listOf(
                    ItemValueOSScreenModel(
                        id = 1,
                        nroOS = 123456,
                        value = null
                    ),
                    ItemValueOSScreenModel(
                        id = 2,
                        nroOS = 456123,
                        value = 10.0
                    )
                ),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavMenuNote = {},
                onNavCollection = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionListFertigationPagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionListFertigationContent(
                list = listOf(
                    ItemValueOSScreenModel(
                        id = 1,
                        nroOS = 123456,
                        value = null
                    ),
                    ItemValueOSScreenModel(
                        id = 2,
                        nroOS = 456123,
                        value = 10.0
                    )
                ),
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavMenuNote = {},
                onNavCollection = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionListFertigationPagePreviewInvalid() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionListFertigationContent(
                list = listOf(
                    ItemValueOSScreenModel(
                        id = 1,
                        nroOS = 123456,
                        value = null
                    ),
                    ItemValueOSScreenModel(
                        id = 2,
                        nroOS = 456123,
                        value = 10.0
                    )
                ),
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                errors = Errors.INVALID_CLOSE_COLLECTION,
                onNavMenuNote = {},
                onNavCollection = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}