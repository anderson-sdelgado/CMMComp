package br.com.usinasantafe.cmm.presenter.view.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.lib.FlowApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavInitialMenu: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavCheckList: () -> Unit
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.startApp()
            }

            SplashContent(
                flowApp = uiState.flowApp,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavInitialMenu = onNavInitialMenu,
                onNavMenuNote = onNavMenuNote,
                onNavCheckList = onNavCheckList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun SplashContent(
    flowApp: FlowApp,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    onNavInitialMenu: () -> Unit,
    onNavMenuNote: () -> Unit,
    onNavCheckList: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visibility by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                visibility = !visibility
                delay(2000)
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(animationSpec = tween(durationMillis = 1100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1100))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.Fit,
                modifier = modifier.size(250.dp)
            )
        }
    }

    if(flagDialog) {
        AlertDialogSimpleDesign(
            text = stringResource(id = R.string.text_failure, failure),
            setCloseDialog = setCloseDialog,
            setActionButtonOK = onNavInitialMenu
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            when(flowApp) {
                FlowApp.HEADER_INITIAL -> onNavInitialMenu()
                FlowApp.NOTE_WORK -> onNavMenuNote()
                FlowApp.CHECK_LIST -> onNavCheckList()
                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                flowApp = FlowApp.HEADER_INITIAL,
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                onNavInitialMenu = {},
                onNavMenuNote = {},
                onNavCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                flowApp = FlowApp.HEADER_INITIAL,
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                onNavInitialMenu = {},
                onNavMenuNote = {},
                onNavCheckList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}