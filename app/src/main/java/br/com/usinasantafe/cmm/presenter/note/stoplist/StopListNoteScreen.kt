package br.com.usinasantafe.cmm.presenter.note.stoplist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.ItemListDesign
import br.com.usinasantafe.cmm.ui.theme.TextButtonDesign
import br.com.usinasantafe.cmm.ui.theme.TitleDesign

const val TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN = "tag_filter_text_field_chave_list_screen"

@Composable
fun StopListNoteScreen() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            StopListNoteContent(
                stopList = emptyList(),
                setIdStop = {},
                field = "",
                onFieldChanged = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun StopListNoteContent(
    stopList: List<Stop>,
    setIdStop: (Int) -> Unit,
    field: String,
    onFieldChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            ),
            value = field,
            onValueChange = onFieldChanged,
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.text_placeholder_field
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN)
        )
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_stop
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(stopList) {
                ItemListDesign(
                    text = it.descrStop,
                    setActionItem = {
                        setIdStop(it.idStop)
                    },
                    id = it.idStop,
                    padding = 6
                )
            }
        }
        Button(
            onClick = {},
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
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

    }
}

@Preview(showBackground = true)
@Composable
fun StopListNotePagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            StopListNoteContent(
                stopList = listOf(
                    Stop(
                        idStop = 1,
                        codStop = 1,
                        descrStop = "PARADA 1"
                    ),
                    Stop(
                        idStop = 2,
                        codStop = 2,
                        descrStop = "PARADA 2"
                    )
                ),
                setIdStop = {},
                field = "",
                onFieldChanged = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}