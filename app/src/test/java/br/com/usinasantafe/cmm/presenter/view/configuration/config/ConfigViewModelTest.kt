package br.com.usinasantafe.cmm.presenter.view.configuration.config

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableActivity
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableBocal
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableColab
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableComponente
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableEquipByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableFrente
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableItemOSMecan
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableLeira
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableMotoMec
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableOS
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableStop
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTablePressaoBocal
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTablePropriedade
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableREquipActivityByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableREquipPneu
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableFunctionActivityStop
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableROSAtiv
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableServico
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableTurn
import br.com.usinasantafe.cmm.presenter.model.ConfigModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.utils.QTD_TABLE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ConfigViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getConfigInternal = mock<GetConfigInternal>()
    private val sendDataConfig = mock<SendDataConfig>()
    private val saveDataConfig = mock<SaveDataConfig>()
    private val updateTableActivity = mock<UpdateTableActivity>()
    private val updateTableBocal = mock<UpdateTableBocal>()
    private val updateTableColab = mock<UpdateTableColab>()
    private val updateTableComponente = mock<UpdateTableComponente>()
    private val updateTableEquipByIdEquip = mock<UpdateTableEquipByIdEquip>()
    private val updateTableFrente = mock<UpdateTableFrente>()
    private val updateTableItemCheckListByNroEquip = mock<UpdateTableItemCheckListByNroEquip>()
    private val updateTableItemOSMecan = mock<UpdateTableItemOSMecan>()
    private val updateTableLeira = mock<UpdateTableLeira>()
    private val updateTableMotoMec = mock<UpdateTableMotoMec>()
    private val updateTableOS = mock<UpdateTableOS>()
    private val updateTablePressaoBocal = mock<UpdateTablePressaoBocal>()
    private val updateTablePropriedade = mock<UpdateTablePropriedade>()
    private val updateTableRActivityStop = mock<UpdateTableRActivityStop>()
    private val updateTableREquipActivityByIdEquip = mock<UpdateTableREquipActivityByIdEquip>()
    private val updateTableREquipPneu = mock<UpdateTableREquipPneu>()
    private val updateTableFunctionActivityStop = mock<UpdateTableFunctionActivityStop>()
    private val updateTableROSAtiv = mock<UpdateTableROSAtiv>()
    private val updateTableServico = mock<UpdateTableServico>()
    private val updateTableStop = mock<UpdateTableStop>()
    private val updateTableTurn = mock<UpdateTableTurn>()
    private val setFinishUpdateAllTable = mock<SetFinishUpdateAllTable>()
    private val sizeAll = (QTD_TABLE * 3) + 1f
    private var contWhenever = 0f
    private var contResult = 0f
    private var contUpdate = 0f

    private val viewModel = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateTableActivity = updateTableActivity,
//        updateTableBocal = updateTableBocal,
        updateTableColab = updateTableColab,
//        updateTableComponente = updateTableComponente,
        updateTableEquipByIdEquip = updateTableEquipByIdEquip,
//        updateTableFrente = updateTableFrente,
        updateTableItemCheckListByNroEquip = updateTableItemCheckListByNroEquip,
//        updateTableItemOSMecan = updateTableItemOSMecan,
//        updateTableLeira = updateTableLeira,
//        updateTableMotoMec = updateTableMotoMec,
//        updateTableOS = updateTableOS,
//        updateTablePressaoBocal = updateTablePressaoBocal,
//        updateTablePropriedade = updateTablePropriedade,
        updateTableRActivityStop = updateTableRActivityStop,
        updateTableREquipActivityByIdEquip = updateTableREquipActivityByIdEquip,
//        updateTableREquipPneu = updateTableREquipPneu,
//        updateTableRFuncaoAtivParada = updateTableRFuncaoAtivParada,
//        updateTableROSAtiv = updateTableROSAtiv,
//        updateTableServico = updateTableServico,
        updateTableStop = updateTableStop,
        updateTableTurn = updateTableTurn,
        setFinishUpdateAllTable = setFinishUpdateAllTable
    )

    @Test
    fun `returnDataConfig - Check return failure if have error in GetConfigInternal`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            viewModel.returnDataConfig()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagFailure,
                true
            )
            assertEquals(
                uiState.failure,
                "ConfigViewModel.returnDataConfig -> Error -> Exception -> java.lang.Exception",
            )
        }

    @Test
    fun `returnDataConfig - Check return correct if function execute successfully and config table internal is empty`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                Result.success(
                    null
                )
            )
            viewModel.returnDataConfig()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                false
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
            assertEquals(
                uiState.failure,
                ""
            )
            assertEquals(
                uiState.number,
                ""
            )
            assertEquals(
                uiState.password,
                ""
            )
        }

    @Test
    fun `returnDataConfig - Check return correct if function execute successfully and config table internal is data`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                Result.success(
                    ConfigModel(
                        number = "16997417840",
                        nroEquip = "310",
                        password = "12345",
                        checkMotoMec = true
                    )
                )
            )
            viewModel.returnDataConfig()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                false
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
            assertEquals(
                uiState.failure,
                ""
            )
            assertEquals(
                uiState.nroEquip,
                "310"
            )
            assertEquals(
                uiState.number,
                "16997417840"
            )
            assertEquals(
                uiState.password,
                "12345"
            )
            assertEquals(
                uiState.checkMotoMec,
                true
            )
        }

    @Test
    fun `saveTokenAndUpdate - Check return failure if number, password or nroEquip is empty`() =
        runTest {
            viewModel.onSaveAndUpdate()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagFailure,
                true
            )
            assertEquals(
                uiState.errors,
                Errors.FIELD_EMPTY
            )
        }

    @Test
    fun `saveTokenAndUpdate - Check return failure if have error in SendDataConfig`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00"
                )
            ).thenReturn(
                resultFailure(
                    "SendDataConfig",
                    "-",
                    Exception()
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            val result = viewModel.token().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.token -> SendDataConfig -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
            viewModel.onSaveAndUpdate()
            assertEquals(
                viewModel.uiState.value.failure,
                "ConfigViewModel.token -> SendDataConfig -> java.lang.Exception"
            )
        }
    
    @Test
    fun `saveTokenAndUpdate - Check return failure if have error in SaveDataConfig`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1,
                        idEquip = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1,
                    idEquip = 1
                )
            ).thenReturn(
                resultFailure(
                    "SaveDataConfig",
                    "-",
                    Exception()
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            val result = viewModel.token().toList()
            assertEquals(result.count(), 3)
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.token -> SaveDataConfig -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
            viewModel.onSaveAndUpdate()
            assertEquals(
                viewModel.uiState.value.failure,
                "ConfigViewModel.token -> SaveDataConfig -> java.lang.Exception"
            )
        }

    @Test
    fun `saveTokenAndUpdate - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1,
                        idEquip = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            val result = viewModel.token().toList()
            assertEquals(result.count(), 3)
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL,
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableActivity`() =
        runTest {
            val qtdBefore = 0f
            whenever(
                updateTableActivity(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanActivity -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanActivity -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableColab`() =
        runTest {
            val qtdBefore = 1f
            wheneverSuccessActivity()
            whenever(
                updateTableColab(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanColab -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableEquip`() =
        runTest {
            val qtdBefore = 2f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            whenever(
                updateTableEquipByIdEquip(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanEquip -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableItemCheckListByNroEquip`() =
        runTest {
            val qtdBefore = 3f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            whenever(
                updateTableItemCheckListByNroEquip(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanItemCheckList -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanItemCheckList -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableRActivityStop`() =
        runTest {
            val qtdBefore = 4f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessItemCheckList()
            whenever(
                updateTableRActivityStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanRActivityStop -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            checkResultUpdateItemCheckList(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanRActivityStop -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableREquipActivity`() =
        runTest {
            val qtdBefore = 5f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessItemCheckList()
            wheneverSuccessRActivityStop()
            whenever(
                updateTableREquipActivityByIdEquip(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_equip_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanREquipActivity -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateRActivityStop(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanREquipActivity -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableStop`() =
        runTest {
            val qtdBefore = 6f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessItemCheckList()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            whenever(
                updateTableStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanStop -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanStop -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableTurn`() =
        runTest {
            val qtdBefore = 7f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessItemCheckList()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessStop()
            whenever(
                updateTableTurn(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanTurn -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            checkResultUpdateStop(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanTurn -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in SetCheckUpdateAllTable`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1,
                        idEquip = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessItemCheckList()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessStop()
            wheneverSuccessTurn()
            whenever(
                setFinishUpdateAllTable()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((QTD_TABLE * 3) + 1).toInt()
            )
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            checkResultUpdateStop(result)
            checkResultUpdateTurn(result)
            assertEquals(
                result[(QTD_TABLE * 3).toInt()],
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = true,
                    currentProgress = 1f,
                    failure = "ConfigViewModel.updateAllDatabase -> Error -> Exception -> java.lang.Exception",
                )
            )
            viewModel.onSaveAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = true,
                    currentProgress = 1f,
                    failure = "ConfigViewModel.updateAllDatabase -> Error -> Exception -> java.lang.Exception",
                )
            )
        }

    @Test
    fun `update - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1,
                        idEquip = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessItemCheckList()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessStop()
            wheneverSuccessTurn()
            whenever(
                setFinishUpdateAllTable()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((QTD_TABLE * 3) + 1).toInt())
            checkResultUpdateActivity(result)
            checkResultUpdateColab(result)
            checkResultUpdateEquip(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            checkResultUpdateStop(result)
            checkResultUpdateTurn(result)
            assertEquals(
                result[(QTD_TABLE * 3).toInt()],
                ConfigState(
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.onSaveAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        }

    private fun wheneverSuccessActivity() =
        runTest {
            whenever(
                updateTableActivity(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateActivity(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
//
//    private fun wheneverSuccessBocal() =
//        runTest {
//            whenever(
//                updateTableBocal(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_bocal",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_bocal",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateBocal(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_bocal",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_bocal",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }

    private fun wheneverSuccessColab() =
        runTest {
            whenever(
                updateTableColab(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateColab(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
//
//    private fun wheneverSuccessComponente() =
//        runTest {
//            whenever(
//                updateTableComponente(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_componente",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_componente do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_componente",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateComponente(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_componente",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_componente do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_componente",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }

    private fun wheneverSuccessEquip() =
        runTest {
            whenever(
                updateTableEquipByIdEquip(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateEquip(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
//
//    private fun wheneverSuccessFrente() =
//        runTest {
//            whenever(
//                updateTableFrente(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_frente",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_frente do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdateModel(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_frente",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateFrente(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_frente",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_frente do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_frente",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }

    private fun wheneverSuccessItemCheckList() =
        runTest {
            whenever(
                updateTableItemCheckListByNroEquip(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateItemCheckList(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
//
//    private fun wheneverSuccessItemOSMecan() =
//        runTest {
//            whenever(
//                updateTableItemOSMecan(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_item_os_mecan",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_item_os_mecan do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_item_os_mecan",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateItemOSMecan(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_item_os_mecan",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_item_os_mecan do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_item_os_mecan",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessLeira() =
//        runTest {
//            whenever(
//                updateTableLeira(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_leira",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_leira",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateLeira(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_leira",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_leira",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessMotoMec() =
//        runTest {
//            whenever(
//                updateTableMotoMec(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_motomec",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_motomec",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateMotoMec(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_motomec",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_motomec",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessOS() =
//        runTest {
//            whenever(
//                updateTableOS(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_os",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_os do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_os",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateOS(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_os",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_os do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_os",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }

    private fun wheneverSuccessStop() =
        runTest {
            whenever(
                updateTableStop(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateStop(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
//
//    private fun wheneverSuccessPressaoBocal() =
//        runTest {
//            whenever(
//                updateTablePressaoBocal(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_pressao_bocal",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_pressao_bocal do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_pressao_bocal",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdatePressaoBocal(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_pressao_bocal",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_pressao_bocal do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_pressao_bocal",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessPropriedade() =
//        runTest {
//            whenever(
//                updateTablePropriedade(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_propriedade",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_propriedade",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdatePropriedade(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_propriedade",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_propriedade",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }

    private fun wheneverSuccessRActivityStop() =
        runTest {
            whenever(
                updateTableRActivityStop(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateRActivityStop(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessREquipActivity() =
        runTest {
            whenever(
                updateTableREquipActivityByIdEquip(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_equip_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_equip_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_equip_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateREquipActivity(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
//
//    private fun wheneverSuccessREquipPneu() =
//        runTest {
//            whenever(
//                updateTableREquipPneu(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_r_equip_pneu",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_r_equip_pneu",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateREquipPneu(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_r_equip_pneu",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_r_equip_pneu",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessRFuncaoAtiv() =
//        runTest {
//            whenever(
//                updateTableFunctionActivityStop(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_r_funcao_ativ",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_r_funcao_ativ",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateRFuncaoAtiv(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_r_funcao_ativ",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_r_funcao_ativ",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessROSAtiv() =
//        runTest {
//            whenever(
//                updateTableROSAtiv(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_r_os_ativ",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_r_os_ativ do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_r_os_ativ",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateROSAtiv(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_r_os_ativ",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_r_os_ativ do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_r_os_ativ",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }
//
//    private fun wheneverSuccessServico() =
//        runTest {
//            whenever(
//                updateTableServico(
//                    sizeAll = sizeAll,
//                    count = ++contUpdate
//                )
//            ).thenReturn(
//                flowOf(
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Limpando a tabela tb_servico",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                    ResultUpdate(
//                        flagProgress = true,
//                        msgProgress = "Salvando dados na tabela tb_servico",
//                        currentProgress = percentage(++contWhenever, sizeAll)
//                    ),
//                )
//            )
//        }
//
//    private fun checkResultUpdateServico(result: List<ConfigState>) =
//        runTest {
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_servico",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//            assertEquals(
//                result[contResult.toInt()],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_servico",
//                    currentProgress = percentage(++contResult, sizeAll)
//                )
//            )
//        }

    private fun wheneverSuccessTurn() =
        runTest {
            whenever(
                updateTableTurn(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateTurn(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

}