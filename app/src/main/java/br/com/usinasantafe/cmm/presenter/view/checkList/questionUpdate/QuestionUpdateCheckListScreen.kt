package br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.TitleDesign

@Composable
fun QuestionUpdateCheckListScreen() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionUpdateCheckListContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "")
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionUpdateCheckListPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionUpdateCheckListContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}