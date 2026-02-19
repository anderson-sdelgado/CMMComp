package br.com.usinasantafe.cmm.presenter.view.fertigation.collection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.errors
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.TextFieldDesign

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = hiltViewModel(),
    onNavCollectionList: () -> Unit,
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CollectionContent(
                nroOS = uiState.nroOS,
                collection = uiState.collection,
                setTextField = viewModel::setTextField,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavCollectionList = onNavCollectionList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun CollectionContent(
    nroOS: String,
    collection: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavCollectionList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

        TitleDesign(
            text = stringResource(
                id = R.string.text_title_os_value,
                nroOS
            )
        )
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_collection
            )
        )
        TextFieldDesign(collection)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
            flagUpdate = false
        )
        BackHandler {
            onNavCollectionList()
        }

        if(flagDialog) {
            val text = errors(errors, failure)
            AlertDialogSimpleDesign(text = text, setCloseDialog = setCloseDialog,)
        }

        LaunchedEffect(flagAccess) {
            if(flagAccess) {
                onNavCollectionList()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CollectionPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionContent(
                nroOS = "20000",
                collection = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavCollectionList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionPagePreviewInvalid() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionContent(
                nroOS = "20000",
                collection = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.INVALID,
                onNavCollectionList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionPagePreviewException() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CollectionContent(
                nroOS = "20000",
                collection = "0,0",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavCollectionList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
