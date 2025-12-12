package br.com.usinasantafe.cmm.presenter.view.configuration.config

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemMenu
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableREquipActivityByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRItemMenuStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
import br.com.usinasantafe.cmm.presenter.model.ConfigModel
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.lib.QTD_TABLE
import br.com.usinasantafe.cmm.lib.TypeEquipMain
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
    private val updateTableColab = mock<UpdateTableColab>()
    private val updateTableEquip = mock<UpdateTableEquip>()
    private val updateTableItemCheckListByNroEquip = mock<UpdateTableItemCheckListByNroEquip>()
    private val updateTableRActivityStop = mock<UpdateTableRActivityStop>()
    private val updateTableREquipActivityByIdEquip = mock<UpdateTableREquipActivityByIdEquip>()
    private val updateTableStop = mock<UpdateTableStop>()
    private val updateTableTurn = mock<UpdateTableTurn>()
    private val updateTableFunctionActivity = mock<UpdateTableFunctionActivity>()
    private val updateTableFunctionStop = mock<UpdateTableFunctionStop>()
    private val updateTableItemMenu = mock<UpdateTableItemMenu>()
    private val updateTableRItemMenuStop = mock<UpdateTableRItemMenuStop>()
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
        updateTableColab = updateTableColab,
        updateTableEquip = updateTableEquip,
        updateTableItemCheckListByNroEquip = updateTableItemCheckListByNroEquip,
        updateTableRActivityStop = updateTableRActivityStop,
        updateTableREquipActivityByIdEquip = updateTableREquipActivityByIdEquip,
        updateTableRItemMenuStop = updateTableRItemMenuStop,
        updateTableStop = updateTableStop,
        updateTableTurn = updateTableTurn,
        setFinishUpdateAllTable = setFinishUpdateAllTable,
        updateTableFunctionActivity = updateTableFunctionActivity,
        updateTableFunctionStop = updateTableFunctionStop,
        updateTableItemMenu = updateTableItemMenu
    )

    @Test
    fun `returnDataConfig - Check return failure if have error in GetConfigInternal`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                resultFailure(
                    "GetConfigInternal",
                    "-",
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
                "ConfigViewModel.returnDataConfig -> GetConfigInternal -> java.lang.Exception",
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
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
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
                "ConfigViewModel.onSaveAndUpdate -> ConfigViewModel.token -> SendDataConfig -> java.lang.Exception"
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
                        idServ = 1,
                        equip = Equip(
                            id = 10,
                            nro = 2200,
                            codClass = 1,
                            descrClass = "TRATOR",
                            codTurnEquip = 1,
                            idCheckList = 1,
                            typeEquipMain = TypeEquipMain.NORMAL,
                            hourMeter = 5000.0,
                            classify = 1,
                            flagMechanic = true,
                            flagTire = true
                        )
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idServ = 1,
                    equip = Equip(
                        id = 10,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquipMain = TypeEquipMain.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
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
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
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
                "ConfigViewModel.onSaveAndUpdate -> ConfigViewModel.token -> SaveDataConfig -> java.lang.Exception"
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
                        idServ = 1,
                        equip = Equip(
                            id = 10,
                            nro = 2200,
                            codClass = 1,
                            descrClass = "TRATOR",
                            codTurnEquip = 1,
                            idCheckList = 1,
                            typeEquipMain = TypeEquipMain.NORMAL,
                            hourMeter = 5000.0,
                            classify = 1,
                            flagMechanic = true,
                            flagTire = true
                        )
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idServ = 1,
                    equip = Equip(
                        id = 10,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquipMain = TypeEquipMain.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
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
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
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
                        tableUpdate = "tb_colab",
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
                    tableUpdate = "tb_colab",
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
                updateTableEquip(
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
    fun `update - Check return failure if have error in UpdateTableFunctionActivity`() =
        runTest {
            val qtdBefore = 3f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            whenever(
                updateTableFunctionActivity(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanFunctionActivity -> java.lang.NullPointerException",
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
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanFunctionActivity -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableFunctionStop`() =
        runTest {
            val qtdBefore = 4f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            whenever(
                updateTableFunctionStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanFunctionStop -> java.lang.NullPointerException",
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
            checkResultUpdateFunctionActivity(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanFunctionStop -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableItemCheckListByNroEquip`() =
        runTest {
            val qtdBefore = 5f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
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
    fun `update - Check return failure if have error in UpdateTableItemMenuPMM`() =
        runTest {
            val qtdBefore = 6f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            whenever(
                updateTableItemMenu(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_menu",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanItemMenu -> java.lang.NullPointerException",
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
            checkResultUpdateItemCheckList(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanItemMenu -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableRActivityStop`() =
        runTest {
            val qtdBefore = 7f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemMenuPMM(result)
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
            val qtdBefore = 8f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemMenuPMM(result)
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
    fun `update - Check return failure if have error in UpdateTableRItemMenuStop`() =
        runTest {
            val qtdBefore = 9f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            whenever(
                updateTableRItemMenuStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_item_menu_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanRItemMenuStop -> java.lang.NullPointerException",
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemMenuPMM(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> CleanRItemMenuStop -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableStop`() =
        runTest {
            val qtdBefore = 10f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessRItemMenuStop()
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemMenuPMM(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            checkResultUpdateRItemMenuStop(result)
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
            val qtdBefore = 11f
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessRItemMenuStop()
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
            checkResultUpdateFunctionActivity(result)
            checkResultUpdateFunctionStop(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemMenuPMM(result)
            checkResultUpdateRActivityStop(result)
            checkResultUpdateREquipActivity(result)
            checkResultUpdateRItemMenuStop(result)
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
                        idServ = 1,
                        equip = Equip(
                            id = 10,
                            nro = 2200,
                            codClass = 1,
                            descrClass = "TRATOR",
                            codTurnEquip = 1,
                            idCheckList = 1,
                            typeEquipMain = TypeEquipMain.NORMAL,
                            hourMeter = 5000.0,
                            classify = 1,
                            flagMechanic = true,
                            flagTire = true
                        )
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idServ = 1,
                    equip = Equip(
                        id = 10,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquipMain = TypeEquipMain.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessRItemMenuStop()
            wheneverSuccessStop()
            wheneverSuccessTurn()
            whenever(
                setFinishUpdateAllTable()
            ).thenReturn(
                resultFailure(
                    "ISetFinishUpdateAllTable",
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
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((QTD_TABLE * 3) + 1).toInt()
            )
            checkResultUpdateActivityFull(result)
            checkResultUpdateColabFull(result)
            checkResultUpdateEquipFull(result)
            checkResultUpdateFunctionActivityFull(result)
            checkResultUpdateFunctionStopFull(result)
            checkResultUpdateItemCheckListFull(result)
            checkResultUpdateItemMenuPMMFull(result)
            checkResultUpdateRActivityStopFull(result)
            checkResultUpdateREquipActivityFull(result)
            checkResultUpdateRItemMenuStopFull(result)
            checkResultUpdateStopFull(result)
            checkResultUpdateTurnFull(result)
            assertEquals(
                result[(QTD_TABLE * 3).toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = false,
                    currentProgress = 1f,
                    failure = "ConfigViewModel.updateAllDatabase -> ISetFinishUpdateAllTable -> java.lang.Exception",
                )
            )
            viewModel.onSaveAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = false,
                    currentProgress = 1f,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_turn",
                    failure = "ConfigViewModel.token -> ConfigViewModel.onSaveAndUpdate -> ConfigViewModel.updateAllDatabase -> ISetFinishUpdateAllTable -> java.lang.Exception",
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
                        idServ = 1,
                        equip = Equip(
                            id = 10,
                            nro = 2200,
                            codClass = 1,
                            descrClass = "TRATOR",
                            codTurnEquip = 1,
                            idCheckList = 1,
                            typeEquipMain = TypeEquipMain.NORMAL,
                            hourMeter = 5000.0,
                            classify = 1,
                            flagMechanic = true,
                            flagTire = true
                        )
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idServ = 1,
                    equip = Equip(
                        id = 10,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquipMain = TypeEquipMain.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessActivity()
            wheneverSuccessColab()
            wheneverSuccessEquip()
            wheneverSuccessFunctionActivity()
            wheneverSuccessFunctionStop()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemMenuPMM()
            wheneverSuccessRActivityStop()
            wheneverSuccessREquipActivity()
            wheneverSuccessRItemMenuStop()
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
            assertEquals(
                result.count(),
                ((QTD_TABLE * 3) + 1).toInt()
            )
            checkResultUpdateActivityFull(result)
            checkResultUpdateColabFull(result)
            checkResultUpdateEquipFull(result)
            checkResultUpdateFunctionActivityFull(result)
            checkResultUpdateFunctionStopFull(result)
            checkResultUpdateItemCheckListFull(result)
            checkResultUpdateItemMenuPMMFull(result)
            checkResultUpdateRActivityStopFull(result)
            checkResultUpdateREquipActivityFull(result)
            checkResultUpdateRItemMenuStopFull(result)
            checkResultUpdateStopFull(result)
            checkResultUpdateTurnFull(result)
            assertEquals(
                result[(QTD_TABLE * 3).toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
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
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
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

    private fun checkResultUpdateActivityFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

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

    private fun checkResultUpdateColabFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessEquip() =
        runTest {
            whenever(
                updateTableEquip(
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

    private fun checkResultUpdateEquipFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessFunctionActivity() =
        runTest {
            whenever(
                updateTableFunctionActivity(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_function_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_function_activity",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateFunctionActivity(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun checkResultUpdateFunctionActivityFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_function_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessFunctionStop() =
        runTest {
            whenever(
                updateTableFunctionStop(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_function_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_function_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateFunctionStop(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun checkResultUpdateFunctionStopFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_function_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

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

    private fun checkResultUpdateItemCheckListFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessItemMenuPMM() =
        runTest {
            whenever(
                updateTableItemMenu(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_menu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_item_menu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_item_menu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateItemMenuPMM(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun checkResultUpdateItemMenuPMMFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_menu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

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

    private fun checkResultUpdateStopFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

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

    private fun checkResultUpdateRActivityStopFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
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

    private fun checkResultUpdateREquipActivityFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessRItemMenuStop() =
        runTest {
            whenever(
                updateTableRItemMenuStop(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_item_menu_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_item_menu_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_item_menu_stop",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateRItemMenuStop(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun checkResultUpdateRItemMenuStopFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_item_menu_stop",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

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

    private fun checkResultUpdateTurnFull(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
}