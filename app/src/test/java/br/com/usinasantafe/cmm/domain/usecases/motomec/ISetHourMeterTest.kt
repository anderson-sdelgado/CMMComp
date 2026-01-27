package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class ISetHourMeterTest {

    private val configRepository = mock<ConfigRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetHourMeter(
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository,
        startWorkManager = startWorkManager,
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository updateHourMeter`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                resultFailure(
                    context = "IEquipRepository.updateHourMeter",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IEquipRepository.updateHourMeter"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setHourMeterInitialHeader - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setHourMeterInitialHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.setHourMeterInitialHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getTypeEquipHeader - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getTypeEquipHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.getTypeEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp REEL_FERT if function execute successfully and getTypeEquipHeader is TypeEquip REEL_FERT - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.REEL_FERT)
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.REEL_FERT
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecSaveHeaderRepository saveHeader - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecSaveHeaderRepository.saveHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecSaveHeaderRepository.saveHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdCheckListByIdEquip - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                resultFailure(
                    context = "IEquipRepository.getIdCheckListByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> ISetHourMeter.checkOpenCheckList -> IEquipRepository.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp HEADER_INITIAL if idCheckList is 0 - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(0)
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_INITIAL
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getIdTurnCheckListLast - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                resultFailure(
                    context = "IConfigRepository.getIdTurnCheckListLast",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> ISetHourMeter.checkOpenCheckList -> IConfigRepository.getIdTurnCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp CHECK_LIST if idTurnCheckListLast is null - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(null)
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.CHECK_LIST
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdTurnHeader - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                resultFailure(
                    context = "IMotoMecRepository.getIdTurnHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> ISetHourMeter.checkOpenCheckList -> IMotoMecRepository.getIdTurnHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp CHECK_LIST if idTurnCheckListLast is different of idTurnCheckList - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(2)
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.CHECK_LIST
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getDateCheckListLast - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getDateCheckListLast()
            ).thenReturn(
                resultFailure(
                    context = "IConfigRepository.getDateCheckListLast",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> ISetHourMeter.checkOpenCheckList -> IConfigRepository.getDateCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp CHECK_LIST if dateCheckListLast is different of date now - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getDateCheckListLast()
            ).thenReturn(
                Result.success(
                    Date(1750708222000)
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.CHECK_LIST
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setHourMeterFinishHeader - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setHourMeterFinishHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.setHourMeterFinishHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository finishHeader - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.finishHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.finishHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.finishHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp HEADER_FINISH if function execute successfully - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                equipRepository.updateHourMeter(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.finishHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                hourMeter = "10.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_FINISH
            )
        }

}