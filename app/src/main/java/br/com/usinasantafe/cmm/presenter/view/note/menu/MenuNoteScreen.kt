package br.com.usinasantafe.cmm.presenter.view.note.menu

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.cmm.BuildConfig
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.cmm.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.cmm.presenter.theme.CMMTheme
import br.com.usinasantafe.cmm.presenter.theme.ItemListDesign
import br.com.usinasantafe.cmm.presenter.theme.TextButtonDesign
import br.com.usinasantafe.cmm.presenter.theme.TitleDesign
import br.com.usinasantafe.cmm.utils.CERTIFICATE
import br.com.usinasantafe.cmm.utils.CHECK_WILL
import br.com.usinasantafe.cmm.utils.COUPLING_TRAILER
import br.com.usinasantafe.cmm.utils.ECM
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowTire
import br.com.usinasantafe.cmm.utils.HOSE_COLLECTION
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.EXIT_MILL
import br.com.usinasantafe.cmm.utils.EXIT_SCALE
import br.com.usinasantafe.cmm.utils.EXIT_TO_DEPOSIT
import br.com.usinasantafe.cmm.utils.EXIT_TO_FIELD
import br.com.usinasantafe.cmm.utils.FINISH_MECHANICAL
import br.com.usinasantafe.cmm.utils.FlowEquipNote
import br.com.usinasantafe.cmm.utils.HISTORY
import br.com.usinasantafe.cmm.utils.LOADING_COMPOUND
import br.com.usinasantafe.cmm.utils.LOADING_INPUT
import br.com.usinasantafe.cmm.utils.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.utils.PCOMP_COMPOUND
import br.com.usinasantafe.cmm.utils.PCOMP_INPUT
import br.com.usinasantafe.cmm.utils.PERFORMANCE
import br.com.usinasantafe.cmm.utils.PMM
import br.com.usinasantafe.cmm.utils.REEL
import br.com.usinasantafe.cmm.utils.RETURN_MILL
import br.com.usinasantafe.cmm.utils.STOP
import br.com.usinasantafe.cmm.utils.TIRE_CHANGE
import br.com.usinasantafe.cmm.utils.TIRE_INFLATION
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeMsg
import br.com.usinasantafe.cmm.utils.UNCOUPLING_TRAILER
import br.com.usinasantafe.cmm.utils.WAITING_UNLOADING
import br.com.usinasantafe.cmm.utils.WEIGHING_LOADED
import br.com.usinasantafe.cmm.utils.WORK

@Composable
fun MenuNoteScreen(
    viewModel: MenuNoteViewModel = hiltViewModel(),
    onNavOS: () -> Unit,
    onNavActivityList: () -> Unit,
    onNavMeasure: () -> Unit,
    onNavListReel: () -> Unit,
    onNavOSListPerformance: () -> Unit,
    onNavTranshipment: () -> Unit,
    onNavImplement: () -> Unit,
    onNavOSListFertigation: () -> Unit,
    onNavOSMechanical: () -> Unit,
    onNavEquipTire: (flowTire: FlowTire) -> Unit,
    onNavMenuCertificate: () -> Unit,
    onNavInfoLocalSugarcaneLoading: () -> Unit,
    onNavUncouplingTrailer: () -> Unit,
    onNavTrailer: () -> Unit,
    onNavProduct: () -> Unit,
    onNavWill: () -> Unit,
    onNavInfoLoadingCompound: () -> Unit,
    onNavHistory: () -> Unit,
) {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.menuList(BuildConfig.FLAVOR_app)
                viewModel.flowEquipNote()
                viewModel.descrEquip()
            }

            MenuNoteContent(
                descrEquip = uiState.descrEquip,
                itemList = uiState.menuList,
                setSelection = viewModel::setSelection,
                flowEquipNote = uiState.flowEquipNote,
                onButtonReturn = viewModel::onButtonReturn,
                idSelection = uiState.idSelection,
                flagAccess = uiState.flagAccess,
                flagFailure = uiState.flagFailure,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                flagDialogCheck = uiState.flagDialogCheck,
                setCloseDialog = viewModel::setCloseDialog,
                onDialogCheck = viewModel::onDialogCheck,
                typeMsg = uiState.typeMsg,
                onNavOS = onNavOS,
                onNavActivityList = onNavActivityList,
                onNavMeasure = onNavMeasure,
                onNavListReel = onNavListReel,
                onNavOSListPerformance = onNavOSListPerformance,
                onNavTranshipment = onNavTranshipment,
                onNavImplement = onNavImplement,
                onNavOSListFertigation = onNavOSListFertigation,
                onNavOSMechanical = onNavOSMechanical,
                onNavEquipTire = onNavEquipTire,
                onNavMenuCertificate = onNavMenuCertificate,
                onNavInfoLocalSugarcaneLoading = onNavInfoLocalSugarcaneLoading,
                onNavUncouplingTrailer = onNavUncouplingTrailer,
                onNavTrailer = onNavTrailer,
                onNavProduct = onNavProduct,
                onNavWill = onNavWill,
                onNavInfoLoadingCompound = onNavInfoLoadingCompound,
                onNavHistory = onNavHistory,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MenuNoteContent(
    descrEquip: String,
    itemList: List<ItemMenuModel>,
    setSelection: (Int) -> Unit,
    flowEquipNote: FlowEquipNote,
    onButtonReturn: () -> Unit,
    idSelection: Int?,
    flagAccess: Boolean,
    flagFailure: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    setCloseDialog: () -> Unit,
    flagDialogCheck: Boolean,
    onDialogCheck: (Pair<Int, String>) -> Unit,
    typeMsg: TypeMsg,
    onNavOS: () -> Unit,
    onNavActivityList: () -> Unit,
    onNavMeasure: () -> Unit,
    onNavListReel: () -> Unit,
    onNavOSListPerformance: () -> Unit,
    onNavTranshipment: () -> Unit,
    onNavImplement: () -> Unit,
    onNavOSListFertigation: () -> Unit,
    onNavOSMechanical: () -> Unit,
    onNavEquipTire: (flowTire: FlowTire) -> Unit,
    onNavMenuCertificate: () -> Unit,
    onNavInfoLocalSugarcaneLoading: () -> Unit,
    onNavUncouplingTrailer: () -> Unit,
    onNavTrailer: () -> Unit,
    onNavProduct: () -> Unit,
    onNavWill: () -> Unit,
    onNavInfoLoadingCompound: () -> Unit,
    onNavHistory: () -> Unit,
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
            items(itemList) { item ->
                ItemListDesign(
                    text = item.descr,
                    setActionItem = {
                        setSelection(item.id)
                    },
                    font = 26
                )
            }
        }
        Button(
            onClick = onButtonReturn,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = when(flowEquipNote){
                    FlowEquipNote.MAIN -> stringResource(id = R.string.text_button_finish_header)
                    FlowEquipNote.SECONDARY -> stringResource(id = R.string.text_button_return_list)
                },
            )
        }
        BackHandler {}

        if(flagDialog) {
            var text = ""
            if(flagFailure){
                text = when(errors) {
                    Errors.INVALID -> stringResource(
                        id = R.string.text_selection_option_invalid,
                        failure
                    )
                    Errors.HEADER_EMPTY -> stringResource(id = R.string.text_header_empty)
                    Errors.NOTE_MECHANICAL_OPEN -> stringResource(id = R.string.text_note_mechanic_open)
                    else -> stringResource(
                        id = R.string.text_failure,
                        failure
                    )
                }

            } else {
                text = when(typeMsg){
                    TypeMsg.NOTE_FINISH_MECHANICAL -> stringResource(id = R.string.text_msg_finish_mechanic)
                    TypeMsg.ITEM_NORMAL -> {
                        val model = itemList.find { it.id == idSelection }!!
                        stringResource(id = R.string.text_msg_initial_activity, model.descr )
                    }
                }
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagDialogCheck){
            var selection: Pair<Int, String>? = null
            if(idSelection != null) {
                val item = itemList.find { it.id == idSelection }!!
                selection = when(item.app.second) {
                    PMM -> item.function
                    else -> 0 to "FAILURE"
                }
            }
            AlertDialogCheckDesign(
                text = when(selection!!.second){
                    FINISH_MECHANICAL -> stringResource(id = R.string.text_msg_finish_mechanic)
                    else -> ""
                },
                setCloseDialog = setCloseDialog,
                setActionButtonYes = { onDialogCheck(selection) },
            )
        }

    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            if(idSelection != null) {
                val item = itemList.find { it.id == idSelection }!!
                when(item.app.second){
                    PMM -> {
                        when(item.function.second){
                            WORK -> {
                                when(flowEquipNote){
                                    FlowEquipNote.MAIN -> onNavMeasure()
                                    FlowEquipNote.SECONDARY -> onNavListReel()
                                }
                            }
                            STOP -> onNavActivityList()
                            PERFORMANCE -> onNavOSListPerformance()
                            TRANSHIPMENT -> onNavTranshipment()
                            HOSE_COLLECTION -> onNavOSListFertigation()
                            IMPLEMENT -> onNavImplement()
                            NOTE_MECHANICAL -> onNavOSMechanical()
                            TIRE_INFLATION -> onNavEquipTire(FlowTire.INFLATION)
                            TIRE_CHANGE -> onNavEquipTire(FlowTire.CHANGE)
                            REEL -> onNavListReel()
                            HISTORY -> onNavHistory()
                        }
                    }
                    ECM -> {
                        when(item.type.second) {
                            CERTIFICATE -> onNavMenuCertificate()
                            EXIT_MILL -> onNavInfoLocalSugarcaneLoading()
                            RETURN_MILL -> onNavOS()
                            UNCOUPLING_TRAILER -> onNavUncouplingTrailer()
                            COUPLING_TRAILER -> onNavTrailer()
                        }
                    }
                    PCOMP_INPUT -> {
                        when(item.type.second) {
                            EXIT_SCALE,
                            EXIT_TO_DEPOSIT -> onNavOS()
                            LOADING_INPUT -> onNavProduct()
                            CHECK_WILL,
                            WEIGHING_LOADED -> onNavInfoLoadingCompound()
                            WAITING_UNLOADING -> onNavWill()
                        }
                    }
                    PCOMP_COMPOUND -> {
                        when(item.type.second) {
                            EXIT_SCALE,
                            EXIT_TO_FIELD -> onNavOS()
                            LOADING_COMPOUND -> onNavWill()
                            CHECK_WILL,
                            WEIGHING_LOADED -> onNavInfoLoadingCompound()
                        }
                    }
                }
            } else {
                when(flowEquipNote){
                    FlowEquipNote.MAIN -> onNavMeasure()
                    FlowEquipNote.SECONDARY -> onNavListReel()
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreview() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemList = listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 1 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                ),
                setSelection = {},
                flowEquipNote = FlowEquipNote.MAIN,
                onButtonReturn = {},
                idSelection = null,
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.INVALID,
                setCloseDialog = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                flagFailure = false,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {},
                onNavOSListPerformance = {},
                onNavUncouplingTrailer = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavEquipTire = {},
                onNavOSMechanical = {},
                onNavImplement = {},
                onNavMenuCertificate = {},
                onNavOSListFertigation = {},
                onNavTrailer = {},
                onNavTranshipment = {},
                onNavInfoLoadingCompound = {},
                onNavWill = {},
                onNavProduct = {},
                onNavHistory = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewFailure() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemList = listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 1 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                ),
                setSelection = {},
                flowEquipNote = FlowEquipNote.MAIN,
                onButtonReturn = {},
                idSelection = null,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                flagDialogCheck = false,
                errors = Errors.EXCEPTION,
                onDialogCheck = {},
                typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                flagFailure = false,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {},
                onNavOSListPerformance = {},
                onNavUncouplingTrailer = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavEquipTire = {},
                onNavOSMechanical = {},
                onNavImplement = {},
                onNavMenuCertificate = {},
                onNavOSListFertigation = {},
                onNavTrailer = {},
                onNavTranshipment = {},
                onNavInfoLoadingCompound = {},
                onNavWill = {},
                onNavProduct = {},
                onNavHistory = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewSelectionInvalid() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemList = listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 1 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                ),
                setSelection = {},
                flowEquipNote = FlowEquipNote.SECONDARY,
                onButtonReturn = {},
                idSelection = null,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                flagDialogCheck = false,
                errors = Errors.INVALID,
                onDialogCheck = {},
                typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                flagFailure = false,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {},
                onNavOSListPerformance = {},
                onNavUncouplingTrailer = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavEquipTire = {},
                onNavOSMechanical = {},
                onNavImplement = {},
                onNavMenuCertificate = {},
                onNavOSListFertigation = {},
                onNavTrailer = {},
                onNavTranshipment = {},
                onNavInfoLoadingCompound = {},
                onNavWill = {},
                onNavProduct = {},
                onNavHistory = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewHeaderEmpty() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemList = listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 1 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                ),
                setSelection = {},
                flowEquipNote = FlowEquipNote.MAIN,
                onButtonReturn = {},
                idSelection = null,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                flagDialogCheck = false,
                errors = Errors.HEADER_EMPTY,
                onDialogCheck = {},
                typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                flagFailure = false,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {},
                onNavOSListPerformance = {},
                onNavUncouplingTrailer = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavEquipTire = {},
                onNavOSMechanical = {},
                onNavImplement = {},
                onNavMenuCertificate = {},
                onNavOSListFertigation = {},
                onNavTrailer = {},
                onNavTranshipment = {},
                onNavInfoLoadingCompound = {},
                onNavWill = {},
                onNavProduct = {},
                onNavHistory = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuHeaderPagePreviewNoteMechanicOpen() {
    CMMTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuNoteContent(
                descrEquip = "2200 - TRATOR",
                itemList = listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 1 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app =  1 to PMM
                    ),
                ),
                setSelection = {},
                flowEquipNote = FlowEquipNote.SECONDARY,
                onButtonReturn = {},
                idSelection = null,
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                setCloseDialog = {},
                flagDialogCheck = false,
                errors = Errors.NOTE_MECHANICAL_OPEN,
                onDialogCheck = {},
                typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                flagFailure = false,
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {},
                onNavOSListPerformance = {},
                onNavUncouplingTrailer = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavEquipTire = {},
                onNavOSMechanical = {},
                onNavImplement = {},
                onNavMenuCertificate = {},
                onNavOSListFertigation = {},
                onNavTrailer = {},
                onNavTranshipment = {},
                onNavInfoLoadingCompound = {},
                onNavWill = {},
                onNavProduct = {},
                onNavHistory = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}