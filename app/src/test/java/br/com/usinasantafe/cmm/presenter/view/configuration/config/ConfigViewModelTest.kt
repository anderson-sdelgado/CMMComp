package br.com.usinasantafe.cmm.presenter.view.configuration.config

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionActivity
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableFunctionStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemMenu
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableNozzle
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTablePressure
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableREquipActivityByIdEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableRItemMenuStop
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
import br.com.usinasantafe.cmm.presenter.model.ConfigModel
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.QTD_TABLE
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
    private val updateTableComponent = mock<UpdateTableComponent>()
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
    private val updateTableService = mock<UpdateTableService>()
    private val updateTableNozzle = mock<UpdateTableNozzle>()
    private val updateTablePressure = mock<UpdateTablePressure>()
    private val setFinishUpdateAllTable = mock<SetFinishUpdateAllTable>()
    private val sizeAll = (QTD_TABLE * 3) + 1f
    private val tableList = listOf(
        "tb_activity", "tb_colab", "tb_component", "tb_equip", "tb_function_activity",
        "tb_function_stop", "tb_item_check_list", "tb_item_menu", "tb_nozzle", "tb_pressure",
        "tb_r_activity_stop", "tb_r_equip_activity", "tb_r_item_menu_stop", "tb_service",
        "tb_stop", "tb_turn"
    )

    private val viewModel = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateTableActivity = updateTableActivity,
        updateTableColab = updateTableColab,
        updateTableComponent = updateTableComponent,
        updateTableEquip = updateTableEquip,
        updateTableItemCheckListByNroEquip = updateTableItemCheckListByNroEquip,
        updateTableRActivityStop = updateTableRActivityStop,
        updateTableREquipActivityByIdEquip = updateTableREquipActivityByIdEquip,
        updateTableRItemMenuStop = updateTableRItemMenuStop,
        updateTableStop = updateTableStop,
        updateTableTurn = updateTableTurn,
        updateTableFunctionActivity = updateTableFunctionActivity,
        updateTableFunctionStop = updateTableFunctionStop,
        updateTableItemMenu = updateTableItemMenu,
        updateTableService = updateTableService,
        updateTableNozzle = updateTableNozzle,
        updateTablePressure = updateTablePressure,
        setFinishUpdateAllTable = setFinishUpdateAllTable
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
                uiState.status.flagDialog,
                true
            )
            assertEquals(
                uiState.status.flagFailure,
                true
            )
            assertEquals(
                uiState.status.failure,
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
                uiState.status.flagDialog,
                false
            )
            assertEquals(
                uiState.status.flagFailure,
                false
            )
            assertEquals(
                uiState.status.failure,
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
                uiState.status.flagDialog,
                false
            )
            assertEquals(
                uiState.status.flagFailure,
                false
            )
            assertEquals(
                uiState.status.failure,
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
                uiState.status.flagDialog,
                true
            )
            assertEquals(
                uiState.status.flagFailure,
                true
            )
            assertEquals(
                uiState.status.errors,
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.GET_TOKEN,
                        currentProgress = percentage(1f, 3f)
                    )
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
                    status = UpdateStatusState(
                        errors = Errors.TOKEN,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.token -> SendDataConfig -> java.lang.Exception",
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.onSaveAndUpdate()
            assertEquals(
                viewModel.uiState.value.status.failure,
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
                            typeEquip = TypeEquip.NORMAL,
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
                        typeEquip = TypeEquip.NORMAL,
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.GET_TOKEN,
                        currentProgress = percentage(1f, 3f)
                    )
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE_TOKEN,
                        currentProgress = percentage(2f, 3f),
                    )
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
                    status = UpdateStatusState(
                        errors = Errors.TOKEN,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.token -> SaveDataConfig -> java.lang.Exception",
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.onSaveAndUpdate()
            assertEquals(
                viewModel.uiState.value.status.failure,
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
                            typeEquip = TypeEquip.NORMAL,
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
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            ).thenReturn(
                Result.success(Unit)
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.GET_TOKEN,
                        currentProgress = percentage(1f, 3f)
                    )
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE_TOKEN,
                        currentProgress = percentage(2f, 3f),
                    )
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL,
                        currentProgress = 1f,
                    )
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
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanActivity -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableColab`() =
        runTest {
            val qtdBefore = 1f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableColab(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableComponent`() =
        runTest {
            val qtdBefore = 2f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableComponent(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanComponent -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanComponent -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableEquip`() =
        runTest {
            val qtdBefore = 3f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableEquip(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableFunctionActivity`() =
        runTest {
            val qtdBefore = 4f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableFunctionActivity(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanFunctionActivity -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableFunctionStop`() =
        runTest {
            val qtdBefore = 5f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableFunctionStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_function_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanFunctionStop -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableItemCheckListByNroEquip`() =
        runTest {
            val qtdBefore = 6f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableItemCheckListByNroEquip(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanItemCheckList -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableItemMenu`() =
        runTest {
            val qtdBefore = 7f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableItemMenu(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_menu",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_menu",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanItemMenu -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableNozzle`() =
        runTest {
            val qtdBefore = 8f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableNozzle(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanNozzle -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanNozzle -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTablePressure`() =
        runTest {
            val qtdBefore = 9f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTablePressure(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanPressure -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanPressure -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableRActivityStop`() =
        runTest {
            val qtdBefore = 10f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableRActivityStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanRActivityStop -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableREquipActivity`() =
        runTest {
            val qtdBefore = 11f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableREquipActivityByIdEquip(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_equip_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_equip_activity",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanREquipActivity -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableRItemMenuStop`() =
        runTest {
            val qtdBefore = 12f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableRItemMenuStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_item_menu_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_item_menu_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanRItemMenuStop -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableService`() =
        runTest {
            val qtdBefore = 13f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableService(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanService -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanService -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableStop`() =
        runTest {
            val qtdBefore = 14f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableStop(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanStop -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableTurn`() =
        runTest {
            val qtdBefore = 15f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableTurn(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
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
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ConfigViewModel.updateAllDatabase -> CleanTurn -> java.lang.NullPointerException",
                    )
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
                            typeEquip = TypeEquip.NORMAL,
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
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            wheneverSuccess(99f)
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
                ((QTD_TABLE * 3)).toInt()
            )
            checkResultUpdateAll(result)
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
                    status = UpdateStatusState(
                        errors = Errors.EXCEPTION,
                        flagFailure = true,
                        flagDialog = true,
                        flagProgress = true,
                        currentProgress = 1f,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL,
                        tableUpdate = "",
                        failure = "ConfigViewModel.token -> ConfigViewModel.onSaveAndUpdate -> ISetFinishUpdateAllTable -> java.lang.Exception",
                    )
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
                            typeEquip = TypeEquip.NORMAL,
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
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            wheneverSuccess(99f)
            whenever(
                setFinishUpdateAllTable()
            ).thenReturn(
                Result.success(Unit)
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
                ((QTD_TABLE * 3)).toInt()
            )
            checkResultUpdateAll(result)
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
                    status = UpdateStatusState(
                        flagDialog = true,
                        flagProgress = true,
                        flagFailure = false,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                        currentProgress = 1f,
                    )
                )
            )
        }

    private fun wheneverSuccess(posTable: Float) =
        runTest {
            var contUpdate = 0f
            var contWhenever = 0f

            val updateFunctions = listOf<
                    suspend (Float, Float) -> Flow<UpdateStatusState>
                    >(
                { sizeAll, count -> updateTableActivity(sizeAll, count) },
                { sizeAll, count -> updateTableColab(sizeAll, count) },
                { sizeAll, count -> updateTableComponent(sizeAll, count) },
                { sizeAll, count -> updateTableEquip(sizeAll, count) },
                { sizeAll, count -> updateTableFunctionActivity(sizeAll, count) },
                { sizeAll, count -> updateTableFunctionStop(sizeAll, count) },
                { sizeAll, count -> updateTableItemCheckListByNroEquip(sizeAll, count) },
                { sizeAll, count -> updateTableItemMenu(sizeAll, count) },
                { sizeAll, count -> updateTableNozzle(sizeAll, count) },
                { sizeAll, count -> updateTablePressure(sizeAll, count) },
                { sizeAll, count -> updateTableRActivityStop(sizeAll, count) },
                { sizeAll, count -> updateTableREquipActivityByIdEquip(sizeAll, count) },
                { sizeAll, count -> updateTableRItemMenuStop(sizeAll, count) },
                { sizeAll, count -> updateTableService(sizeAll, count) },
                { sizeAll, count -> updateTableStop(sizeAll, count) },
                { sizeAll, count -> updateTableTurn(sizeAll, count) }
            )

            for(func in updateFunctions) {
                whenever(
                    func(
                        sizeAll,
                        ++contUpdate
                    )
                ).thenReturn(
                    flowOf(
                        UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.RECOVERY,
                            tableUpdate = tableList[contUpdate.toInt() - 1],
                            currentProgress = percentage(++contWhenever, sizeAll)
                        ),
                        UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.CLEAN,
                            tableUpdate = tableList[contUpdate.toInt() - 1],
                            currentProgress = percentage(++contWhenever, sizeAll)
                        ),
                        UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.SAVE,
                            tableUpdate = tableList[contUpdate.toInt() - 1],
                            currentProgress = percentage(++contWhenever, sizeAll)
                        ),
                    )
                )
                if(posTable == contUpdate) break
            }
        }

    private fun checkResultUpdate(posTable: Float, result: List<ConfigState>) =
        runTest {
            var contUpdate = 0f
            var cont = 0
            for(table in tableList) {
                assertEquals(
                    result[cont++],
                    ConfigState(
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.RECOVERY,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                assertEquals(
                    result[cont++],
                    ConfigState(
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.CLEAN,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                assertEquals(
                    result[cont++],
                    ConfigState(
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.SAVE,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                ++contUpdate
                if(posTable == contUpdate) break
            }
        }


    private fun checkResultUpdateAll(result: List<ConfigState>) =
        runTest {
            var contUpdate = 0f
            var cont = 0
            for(table in tableList) {
                assertEquals(
                    result[cont++],
                    ConfigState(
                        number = "16997417840",
                        password = "12345",
                        nroEquip = "310",
                        app = "pmm",
                        version = "1.00",
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.RECOVERY,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                assertEquals(
                    result[cont++],
                    ConfigState(
                        number = "16997417840",
                        password = "12345",
                        nroEquip = "310",
                        app = "pmm",
                        version = "1.00",
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.CLEAN,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                assertEquals(
                    result[cont++],
                    ConfigState(
                        number = "16997417840",
                        password = "12345",
                        nroEquip = "310",
                        app = "pmm",
                        version = "1.00",
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.SAVE,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                ++contUpdate
            }
        }

}