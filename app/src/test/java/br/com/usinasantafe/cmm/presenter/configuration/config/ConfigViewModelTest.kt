package br.com.usinasantafe.cmm.presenter.configuration.config

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.cmm.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateAtividade
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateBocal
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateColab
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateComponente
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateEquipPneu
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateEquipSeg
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateFrente
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateItemOSMecan
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateLeira
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateMotoMec
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateOS
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdatePressaoBocal
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdatePropriedade
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateRAtivParada
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateREquipPneu
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateRFuncaoAtiv
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateROSAtiv
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateServico
import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.UpdateTurno
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.percentage
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
    private val updateAtividade = mock<UpdateAtividade>()
    private val updateBocal = mock<UpdateBocal>()
    private val updateColab = mock<UpdateColab>()
    private val updateComponente = mock<UpdateComponente>()
    private val updateEquipPneu = mock<UpdateEquipPneu>()
    private val updateEquipSeg = mock<UpdateEquipSeg>()
    private val updateFrente = mock<UpdateFrente>()
    private val updateItemCheckList = mock<UpdateItemCheckList>()
    private val updateItemOSMecan = mock<UpdateItemOSMecan>()
    private val updateLeira = mock<UpdateLeira>()
    private val updateMotoMec = mock<UpdateMotoMec>()
    private val updateOS = mock<UpdateOS>()
    private val updateParada = mock<UpdateParada>()
    private val updatePressaoBocal = mock<UpdatePressaoBocal>()
    private val updatePropriedade = mock<UpdatePropriedade>()
    private val updateRAtivParada = mock<UpdateRAtivParada>()
    private val updateREquipPneu = mock<UpdateREquipPneu>()
    private val updateRFuncaoAtiv = mock<UpdateRFuncaoAtiv>()
    private val updateROSAtiv = mock<UpdateROSAtiv>()
    private val updateServico = mock<UpdateServico>()
    private val updateTurno = mock<UpdateTurno>()
    private val setCheckUpdateAllTable = mock<SetCheckUpdateAllTable>()
    private val sizeAll = 64f
    private var contWhenever = 0f
    private var contResult = 0f
    private var contUpdate = 0f

    private val viewModel = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateAtividade = updateAtividade,
        updateBocal = updateBocal,
        updateColab = updateColab,
        updateComponente = updateComponente,
        updateEquipPneu = updateEquipPneu,
        updateEquipSeg = updateEquipSeg,
        updateFrente = updateFrente,
        updateItemCheckList = updateItemCheckList,
        updateItemOSMecan = updateItemOSMecan,
        updateLeira = updateLeira,
        updateMotoMec = updateMotoMec,
        updateOS = updateOS,
        updateParada = updateParada,
        updatePressaoBocal = updatePressaoBocal,
        updatePropriedade = updatePropriedade,
        updateRAtivParada = updateRAtivParada,
        updateREquipPneu = updateREquipPneu,
        updateRFuncaoAtiv = updateRFuncaoAtiv,
        updateROSAtiv = updateROSAtiv,
        updateServico = updateServico,
        updateTurno = updateTurno,
        setCheckUpdateAllTable = setCheckUpdateAllTable
    )

    @Test
    fun `returnDataConfig - Check return failure if have error in GetConfigInternal`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetConfigInternal",
                        cause = Exception()
                    )
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
                "ConfigViewModel.returnDataConfig -> GetConfigInternal -> Failure Usecase -> GetConfigInternal -> java.lang.Exception"
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
            viewModel.saveTokenAndUpdate()
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
                Errors.FIELDEMPTY
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
                Result.failure(
                    UsecaseException(
                        function = "GetConfigInternal",
                        cause = Exception()
                    )
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
                    msgProgress = "Enviando dados de Token",
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.token -> SendDataConfig -> Failure Usecase -> GetConfigInternal -> java.lang.Exception",
                    msgProgress = "ConfigViewModel.token -> SendDataConfig -> Failure Usecase -> GetConfigInternal -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
            viewModel.saveTokenAndUpdate()
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "ConfigViewModel.token -> SendDataConfig -> Failure Usecase -> GetConfigInternal -> java.lang.Exception"
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
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveDataConfig",
                        cause = Exception()
                    )
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
                    msgProgress = "Enviando dados de Token",
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados de Token",
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.token -> SaveDataConfig -> Failure Usecase -> SaveDataConfig -> java.lang.Exception",
                    msgProgress = "ConfigViewModel.token -> SaveDataConfig -> Failure Usecase -> SaveDataConfig -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
            viewModel.saveTokenAndUpdate()
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "ConfigViewModel.token -> SaveDataConfig -> Failure Usecase -> SaveDataConfig -> java.lang.Exception"
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
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
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
                    msgProgress = "Enviando dados de Token",
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados de Token",
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Ajuste iniciais finalizado com sucesso!",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `update - Check return configState main if have error in UpdateAtividade`() =
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
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                updateAtividade(
                    sizeAll = sizeAll,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_atividade",
                        currentProgress = percentage(1f, sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        flagProgress = true,
                        currentProgress = 1f,
                        failure = "Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                    )
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            viewModel.saveTokenAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    errors = Errors.UPDATE,
                    flagProgress = true,
                    msgProgress = "Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanAtividade -> java.lang.NullPointerException"
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateAtividade`() =
        runTest {
            whenever(
                updateAtividade(
                    sizeAll = sizeAll,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_atividade",
                        currentProgress = percentage(1f, sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_atividade",
                    currentProgress = percentage(1f, sizeAll)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanAtividade -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateBocal`() =
        runTest {
            val qtdBefore = 1f
            wheneverSuccessAtividade()
            whenever(
                updateBocal(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_bocal",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanBocal -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanBocal -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_bocal",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanBocal -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanBocal -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateColab`() =
        runTest {
            val qtdBefore = 2f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            whenever(
                updateColab(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateComponente`() =
        runTest {
            val qtdBefore = 3f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            whenever(
                updateComponente(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_componente",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanComponente -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanComponente -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_componente",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanComponente -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanComponente -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateEquipPneu`() =
        runTest {
            val qtdBefore = 4f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            whenever(
                updateEquipPneu(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_equip_pneu",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanEquipPneu -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanEquipPneu -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip_pneu",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanEquipPneu -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanEquipPneu -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateEquipSeg`() =
        runTest {
            val qtdBefore = 5f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            whenever(
                updateEquipSeg(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_equip_seg",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanEquipSeg -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanEquipSeg -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip_seg",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanEquipSeg -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanEquipSeg -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateFrente`() =
        runTest {
            val qtdBefore = 6f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            whenever(
                updateFrente(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_frente",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanFrente -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanFrente -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_frente",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanFrente -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanFrente -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateItemCheckList`() =
        runTest {
            val qtdBefore = 7f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            whenever(
                updateItemCheckList(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_item_checklist",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanItemCheckList -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanItemCheckList -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_item_checklist",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanItemCheckList -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanItemCheckList -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateItemOSMecan`() =
        runTest {
            val qtdBefore = 8f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            whenever(
                updateItemOSMecan(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_item_os_mecan",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanItemOSMecan -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanItemOSMecan -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_item_os_mecan",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanItemOSMecan -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanItemOSMecan -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateLeira`() =
        runTest {
            val qtdBefore = 9f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            whenever(
                updateLeira(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_leira",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanLeira -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanLeira -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_leira",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanLeira -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanLeira -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return configState main if have error in UpdateMotoMec`() =
        runTest {
            val qtdBefore = 10f
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            whenever(
                updateMotoMec(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_moto_mec",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        flagProgress = true,
                        currentProgress = 1f,
                        failure = "Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                    )
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            viewModel.saveTokenAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    errors = Errors.UPDATE,
                    flagProgress = true,
                    currentProgress = 1f,
                    flagDialog = true,
                    flagFailure = true,
                    msgProgress = "Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException"
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateMotoMec`() =
        runTest {
            val qtdBefore = 10f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            whenever(
                updateMotoMec(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_moto_mec",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_moto_mec",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanMotoMec -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateOS`() =
        runTest {
            val qtdBefore = 11f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            whenever(
                updateOS(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_os",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanOS -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanOS -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_os",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanOS -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanOS -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateParada`() =
        runTest {
            val qtdBefore = 12f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            whenever(
                updateParada(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_parada",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanParada -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanParada -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_parada",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanParada -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanParada -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdatePressaoBocal`() =
        runTest {
            val qtdBefore = 13f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            whenever(
                updatePressaoBocal(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_pressao_bocal",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanPressaoBocal -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanPressaoBocal -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_pressao_bocal",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanPressaoBocal -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanPressaoBocal -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdatePropriedade`() =
        runTest {
            val qtdBefore = 14f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            whenever(
                updatePropriedade(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_propriedade",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanPropriedade -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanPropriedade -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_propriedade",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanPropriedade -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanPropriedade -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateRAtivParada`() =
        runTest {
            val qtdBefore = 15f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            whenever(
                updateRAtivParada(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_ativ_parada",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanRAtivParada -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanRAtivParada -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_ativ_parada",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanRAtivParada -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanRAtivParada -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateREquipPneu`() =
        runTest {
            val qtdBefore = 16f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            whenever(
                updateREquipPneu(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_equip_pneu",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanREquipPneu -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanREquipPneu -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_equip_pneu",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanREquipPneu -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanREquipPneu -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateRFuncaoAtiv`() =
        runTest {
            val qtdBefore = 17f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            whenever(
                updateRFuncaoAtiv(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_funcao_ativ",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanRFuncaoAtiv -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanRFuncaoAtiv -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            checkResultUpdateREquipPneu(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_funcao_ativ",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanRFuncaoAtiv -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanRFuncaoAtiv -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateROSAtiv`() =
        runTest {
            val qtdBefore = 18f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            wheneverSuccessRFuncaoAtiv()
            whenever(
                updateROSAtiv(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_os_ativ",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanROSAtiv -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanROSAtiv -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            checkResultUpdateREquipPneu(result)
            checkResultUpdateRFuncaoAtiv(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_os_ativ",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanROSAtiv -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanROSAtiv -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateServico`() =
        runTest {
            val qtdBefore = 19f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            wheneverSuccessRFuncaoAtiv()
            wheneverSuccessROSAtiv()
            whenever(
                updateServico(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_servico",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanServico -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanServico -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            checkResultUpdateREquipPneu(result)
            checkResultUpdateRFuncaoAtiv(result)
            checkResultUpdateROSAtiv(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_servico",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanServico -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanServico -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTurno`() =
        runTest {
            val qtdBefore = 20f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            wheneverSuccessRFuncaoAtiv()
            wheneverSuccessROSAtiv()
            wheneverSuccessServico()
            wheneverSuccessTurno()
            whenever(
                updateTurno(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_turno",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            checkResultUpdateREquipPneu(result)
            checkResultUpdateRFuncaoAtiv(result)
            checkResultUpdateROSAtiv(result)
            checkResultUpdateServico(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_turno",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return configState main if have error in UpdateTurno`() =
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
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val qtdBefore = 20f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            wheneverSuccessRFuncaoAtiv()
            wheneverSuccessROSAtiv()
            wheneverSuccessServico()
            wheneverSuccessTurno()
            whenever(
                updateTurno(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_turno",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        flagProgress = true,
                        currentProgress = 1f,
                        failure = "Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                    )
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onNroEquipChanged("310")
            viewModel.setConfigMain(
                version = "1.00",
                app = "pmm"
            )
            viewModel.saveTokenAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    errors = Errors.UPDATE,
                    flagProgress = true,
                    currentProgress = 1f,
                    flagDialog = true,
                    flagFailure = true,
                    msgProgress = "Failure Usecase -> CleanTurno -> java.lang.NullPointerException",
                    failure = "ConfigViewModel.update -> Failure Usecase -> CleanTurno -> java.lang.NullPointerException"
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
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val qtdBefore = 21f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            wheneverSuccessRFuncaoAtiv()
            wheneverSuccessROSAtiv()
            wheneverSuccessServico()
            wheneverSuccessTurno()
            whenever(
                setCheckUpdateAllTable(
                    FlagUpdate.UPDATED
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SetCheckUpdateAllTable",
                        cause = Exception()
                    )
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
            assertEquals(result.count(), ((qtdBefore * 3) + 1).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            checkResultUpdateREquipPneu(result)
            checkResultUpdateRFuncaoAtiv(result)
            checkResultUpdateROSAtiv(result)
            checkResultUpdateServico(result)
            checkResultUpdateTurno(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = true,
                    currentProgress = 1f,
                    failure = "ConfigViewModel.update -> SetCheckUpdateAllTable -> Failure Usecase -> SetCheckUpdateAllTable -> java.lang.Exception",
                    msgProgress = "ConfigViewModel.update -> SetCheckUpdateAllTable -> Failure Usecase -> SetCheckUpdateAllTable -> java.lang.Exception",
                )
            )
            viewModel.saveTokenAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = true,
                    currentProgress = 1f,
                    msgProgress = "ConfigViewModel.update -> SetCheckUpdateAllTable -> Failure Usecase -> SetCheckUpdateAllTable -> java.lang.Exception",
                    failure = "ConfigViewModel.update -> SetCheckUpdateAllTable -> Failure Usecase -> SetCheckUpdateAllTable -> java.lang.Exception"
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
                Result.success(1)
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    nroEquip = "310",
                    app = "pmm",
                    version = "1.00",
                    checkMotoMec = true,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val qtdBefore = 21f
            wheneverSuccessAtividade()
            wheneverSuccessBocal()
            wheneverSuccessColab()
            wheneverSuccessComponente()
            wheneverSuccessEquipPneu()
            wheneverSuccessEquipSeg()
            wheneverSuccessFrente()
            wheneverSuccessItemCheckList()
            wheneverSuccessItemOSMecan()
            wheneverSuccessLeira()
            wheneverSuccessMotoMec()
            wheneverSuccessOS()
            wheneverSuccessParada()
            wheneverSuccessPressaoBocal()
            wheneverSuccessPropriedade()
            wheneverSuccessRAtivParada()
            wheneverSuccessREquipPneu()
            wheneverSuccessRFuncaoAtiv()
            wheneverSuccessROSAtiv()
            wheneverSuccessServico()
            wheneverSuccessTurno()
            whenever(
                setCheckUpdateAllTable(
                    FlagUpdate.UPDATED
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
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), ((qtdBefore * 3) + 1).toInt())
            checkResultUpdateAtividade(result)
            checkResultUpdateBocal(result)
            checkResultUpdateColab(result)
            checkResultUpdateComponente(result)
            checkResultUpdateEquipPneu(result)
            checkResultUpdateEquipSeg(result)
            checkResultUpdateFrente(result)
            checkResultUpdateItemCheckList(result)
            checkResultUpdateItemOSMecan(result)
            checkResultUpdateLeira(result)
            checkResultUpdateMotoMec(result)
            checkResultUpdateOS(result)
            checkResultUpdateParada(result)
            checkResultUpdatePressaoBocal(result)
            checkResultUpdatePropriedade(result)
            checkResultUpdateRAtivParada(result)
            checkResultUpdateREquipPneu(result)
            checkResultUpdateRFuncaoAtiv(result)
            checkResultUpdateROSAtiv(result)
            checkResultUpdateServico(result)
            checkResultUpdateTurno(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            viewModel.saveTokenAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
        }

    private fun wheneverSuccessAtividade() =
        runTest {
            whenever(
                updateAtividade(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_atividade",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_atividade do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_atividade",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateAtividade(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_atividade",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_atividade do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_atividade",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessBocal() =
        runTest {
            whenever(
                updateBocal(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_bocal",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_bocal",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateBocal(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_bocal",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_bocal",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessColab() =
        runTest {
            whenever(
                updateColab(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_colab",
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
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessComponente() =
        runTest {
            whenever(
                updateComponente(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_componente",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_componente do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_componente",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateComponente(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_componente",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_componente do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_componente",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessEquipPneu() =
        runTest {
            whenever(
                updateEquipPneu(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_equip_pneu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_equip_pneu do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_equip_pneu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateEquipPneu(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip_pneu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip_pneu do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip_pneu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessEquipSeg() =
        runTest {
            whenever(
                updateEquipSeg(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_equip_seg",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_equip_seg do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_equip_seg",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateEquipSeg(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip_seg",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip_seg do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip_seg",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessFrente() =
        runTest {
            whenever(
                updateFrente(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_frente",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_frente do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_frente",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateFrente(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_frente",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_frente do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_frente",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessItemCheckList() =
        runTest {
            whenever(
                updateItemCheckList(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_item_checklist",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_item_checklist do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_item_checklist",
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
                    msgProgress = "Limpando a tabela tb_item_checklist",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_item_checklist do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_item_checklist",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessItemOSMecan() =
        runTest {
            whenever(
                updateItemOSMecan(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_item_os_mecan",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_item_os_mecan do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_item_os_mecan",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateItemOSMecan(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_item_os_mecan",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_item_os_mecan do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_item_os_mecan",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessLeira() =
        runTest {
            whenever(
                updateLeira(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_leira",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_leira",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateLeira(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_leira",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_leira",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessMotoMec() =
        runTest {
            whenever(
                updateMotoMec(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_motomec",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_motomec",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateMotoMec(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_motomec",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_motomec",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessOS() =
        runTest {
            whenever(
                updateOS(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_os",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_os do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_os",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateOS(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_os",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_os do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_os",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessParada() =
        runTest {
            whenever(
                updateParada(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_parada",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_parada do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_parada",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateParada(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_parada",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_parada do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_parada",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessPressaoBocal() =
        runTest {
            whenever(
                updatePressaoBocal(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_pressao_bocal",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_pressao_bocal do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_pressao_bocal",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdatePressaoBocal(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_pressao_bocal",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_pressao_bocal do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_pressao_bocal",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessPropriedade() =
        runTest {
            whenever(
                updatePropriedade(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_propriedade",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_propriedade",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdatePropriedade(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_propriedade",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_propriedade",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessRAtivParada() =
        runTest {
            whenever(
                updateRAtivParada(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_ativ_parada",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_r_ativ_parada do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_r_ativ_parada",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateRAtivParada(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_ativ_parada",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_ativ_parada do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_ativ_parada",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessREquipPneu() =
        runTest {
            whenever(
                updateREquipPneu(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_equip_pneu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_r_equip_pneu",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateREquipPneu(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_equip_pneu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_equip_pneu",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessRFuncaoAtiv() =
        runTest {
            whenever(
                updateRFuncaoAtiv(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_funcao_ativ",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_r_funcao_ativ",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateRFuncaoAtiv(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_funcao_ativ",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_funcao_ativ",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessROSAtiv() =
        runTest {
            whenever(
                updateROSAtiv(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_r_os_ativ",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_r_os_ativ do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_r_os_ativ",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateROSAtiv(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_os_ativ",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_os_ativ do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_os_ativ",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessServico() =
        runTest {
            whenever(
                updateServico(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_servico",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_servico",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateServico(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_servico",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_servico",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessTurno() =
        runTest {
            whenever(
                updateTurno(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_turno",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_turno",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateTurno(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_turno",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_turno",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

}