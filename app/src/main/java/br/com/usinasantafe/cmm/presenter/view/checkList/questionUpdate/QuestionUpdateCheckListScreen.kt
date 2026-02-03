package br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogProgressDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.theme.MsgUpdate
import br.com.usinasantafe.cmm.presenter.theme.ProgressUpdate
import br.com.usinasantafe.cmm.utils.UpdateStatusState

@Composable
fun QuestionUpdateCheckListScreen(
    viewModel: QuestionUpdateCheckListViewModel = hiltViewModel(),
    onNavItemCheckList: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.checkUpdate()
            }

            QuestionUpdateCheckListContent(
                flagCheckUpdate = uiState.flagCheckUpdate,
                updateDatabase = viewModel::updateDatabase,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                status = uiState.status,
                onNavItemCheckList = onNavItemCheckList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionUpdateCheckListContent(
    flagCheckUpdate: Boolean,
    updateDatabase: () -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    status: UpdateStatusState,
    onNavItemCheckList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            TitleDesign(
                text = stringResource(
                    id = R.string.text_title_check_list
                )
            )
        }
        if(flagCheckUpdate){
            Column(
                modifier = modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )
                Spacer(
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = stringResource(
                        id = R.string.text_msg_check_update_check_list
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = onNavItemCheckList,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButtonDesign(
                        text = stringResource(
                            id = R.string.text_pattern_cancel
                        )
                    )
                }
            }
        } else {
            Column(
                modifier = modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(
                        id = R.string.text_msg_update_check_list
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = onNavItemCheckList,
                        modifier = Modifier.weight(1f)
                    ) {
                        TextButtonDesign(
                            text = stringResource(
                                id = R.string.text_pattern_no
                            )
                        )
                    }
                    Button(
                        onClick = updateDatabase,
                        modifier = Modifier.weight(1f),
                    ) {
                        TextButtonDesign(
                            text = stringResource(
                                id = R.string.text_pattern_yes
                            )
                        )
                    }
                }
            }
        }
        BackHandler {}


        if (status.flagDialog) {
            MsgUpdate(status = status, setCloseDialog = setCloseDialog, value = stringResource(id = R.string.text_title_operator))
        }

        if (status.flagProgress) {
            ProgressUpdate(status)
        }
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavItemCheckList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QuestionUpdateCheckListPagePreviewCheckUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                flagCheckUpdate = true,
                updateDatabase = {},
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    failure = "",
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0.0f,
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                ),
                onNavItemCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionUpdateCheckListPagePreviewMsgUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                flagCheckUpdate = false,
                updateDatabase = {},
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    failure = "",
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0.0f,
                ),
                onNavItemCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionUpdateCheckListPagePreviewMsgFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                flagCheckUpdate = false,
                updateDatabase = {},
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = true,
                    failure = "Failure",
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0.0f,
                ),
                onNavItemCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionUpdateCheckListPagePreviewMsgFailureUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                flagCheckUpdate = false,
                updateDatabase = {},
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = true,
                    failure = "Failure",
                    flagFailure = true,
                    errors = Errors.UPDATE,
                    flagProgress = false,
                    levelUpdate = null,
                    tableUpdate = "",
                    currentProgress = 0.0f,
                ),
                onNavItemCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuestionUpdateCheckListPagePreviewUpdate() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                flagCheckUpdate = false,
                updateDatabase = {},
                setCloseDialog = {},
                flagAccess = false,
                status = UpdateStatusState(
                    flagDialog = false,
                    failure = "",
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = 0.333f,
                ),
                onNavItemCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}