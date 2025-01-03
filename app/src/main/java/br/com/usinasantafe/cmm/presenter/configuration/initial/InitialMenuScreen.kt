import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.usinasantafe.cmm.presenter.configuration.initial.InitialMenuViewModel
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.TitleDesign

@Composable
fun InitialMenuScreen(
    viewModel: InitialMenuViewModel = hiltViewModel()
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun InitialMenuContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "MENU INICIAL")
    }
}

@Preview(showBackground = true)
@Composable
fun InitialMenuPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}