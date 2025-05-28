package br.com.usinasantafe.cmm.presenter.note.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.ui.theme.CMMTheme
import br.com.usinasantafe.cmm.ui.theme.ItemListDesign
import br.com.usinasantafe.cmm.ui.theme.TextButtonDesign
import br.com.usinasantafe.cmm.ui.theme.TitleDesign

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel()
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuContent(
                descrEquip = uiState.descrEquip,
                itemMenuModelList = uiState.menuList,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MenuContent(
    descrEquip: String = "",
    itemMenuModelList: List<ItemMenuModel> = listOf(),
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_descr_equip,
                descrEquip
            ),
            font = 26,
            padding = 6
        )
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_menu
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(itemMenuModelList) { item ->
                ItemListDesign(
                    text = item.title,
                    setActionItem = {
//                        setIdActivity(activity.idActivity)
                    },
                    font = 26
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_finish_header
                )
            )
        }
        BackHandler {}
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuContent(
                descrEquip = "2200 - TRATOR",
                itemMenuModelList = listOf(
                    ItemMenuModel(
                        id = 1,
                        title = "TRABALHANDO"
                    ),
                    ItemMenuModel(
                        id = 2,
                        title = "PARADO"
                    ),
                ),
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}