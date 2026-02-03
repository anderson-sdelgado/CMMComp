package br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.presenter.theme.ButtonMaxWidth

@Composable
fun ItemCheckListScreen(
    viewModel: ItemCheckListViewModel = hiltViewModel(),
    onNavMenuNote: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.get()
            }

            ItemCheckListContent(
                pos = uiState.pos,
                descr = uiState.descr,
                ret = viewModel::ret,
                set = viewModel::set,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavMenuNote = onNavMenuNote,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ItemCheckListContent(
    pos: Int,
    descr: String,
    ret: () -> Unit,
    set: (OptionRespCheckList) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    onNavMenuNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_check_list
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
                text = "$pos - $descr",
                fontSize = 28.sp,
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        ButtonMaxWidth(R.string.text_button_according) {
            set(OptionRespCheckList.ACCORDING)
        }

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        ButtonMaxWidth(R.string.text_button_analyze) {
            set(OptionRespCheckList.ACCORDING)
        }

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        ButtonMaxWidth(R.string.text_button_repair) {
            set(OptionRespCheckList.REPAIR)
        }

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        ButtonMaxWidth(R.string.text_pattern_return) {
            ret()
        }

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog
            )
        }

        LaunchedEffect(flagAccess) {
            if(flagAccess) {
                onNavMenuNote()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ItemCheckListPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ItemCheckListContent(
                pos = 1,
                descr = "Item 1",
                ret = {},
                set = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCheckListPagePreviewWithFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ItemCheckListContent(
                pos = 1,
                descr = "Item 1",
                ret = {},
                set = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                onNavMenuNote = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}