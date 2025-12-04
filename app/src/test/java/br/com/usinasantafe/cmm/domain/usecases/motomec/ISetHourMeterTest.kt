package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetHourMeterTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetHourMeter(
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setHourMeterInitial - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.setHourMeterInitial",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IHeaderMotoMecRepository.setHourMeterInitial"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getIdEquip - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.getIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IHeaderMotoMecRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository updateHourMeterByIdEquip - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateHourMeterByIdEquip(
                    hourMeter = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IEquipRepository.updateHourMeterByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IEquipRepository.updateHourMeterByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository save - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateHourMeterByIdEquip(
                    hourMeter = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.save",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IHeaderMotoMecRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterInitialHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateHourMeterByIdEquip(
                    hourMeter = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setHourMeterInitial - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.setHourMeterFinish",
                    message = "-",
                    cause = Exception()
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
                "ISetHourMeter -> IHeaderMotoMecRepository.setHourMeterFinish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getIdEquip - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.getIdEquip",
                    message = "-",
                    cause = Exception()
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
                "ISetHourMeter -> IHeaderMotoMecRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository updateHourMeterByIdEquip - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateHourMeterByIdEquip(
                    hourMeter = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IEquipRepository.updateHourMeterByIdEquip",
                    message = "-",
                    cause = Exception()
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
                "ISetHourMeter -> IEquipRepository.updateHourMeterByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in  HeaderMotoMecRepository close - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateHourMeterByIdEquip(
                    hourMeter = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.finishHeader()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.close",
                    message = "-",
                    cause = Exception()
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
                "ISetHourMeter -> IHeaderMotoMecRepository.close"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp HEADER_FINISH`() =
        runTest {
            whenever(
                motoMecRepository.setHourMeterFinishHeader(
                    hourMeter = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateHourMeterByIdEquip(
                    hourMeter = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.finishHeader()
            ).thenReturn(
                Result.success(true)
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
                true
            )
        }

}