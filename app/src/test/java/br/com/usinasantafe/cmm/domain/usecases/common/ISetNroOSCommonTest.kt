package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetNroOSCommonTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ISetNroOSCommon(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if value of field is incorrect - FlowApp HEADER_DEFAULT`() =
        runTest {
            val result = usecase(
                nroOS = "19759a",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOSCommon"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setNroOS - FlowApp HEADER_DEFAULT`() =
        runTest {
            whenever(
                motoMecRepository.setNroOSHeader(123456)
            ).thenReturn(
                resultFailure(
                    "HeaderMotoMecRepository.setNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOSCommon -> HeaderMotoMecRepository.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp HEADER_DEFAULT`() =
        runTest {
            whenever(
                motoMecRepository.setNroOSHeader(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
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

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setNroOSHeader(123456)
            ).thenReturn(
                resultFailure(
                    "HeaderMotoMecRepository.setNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOSCommon -> HeaderMotoMecRepository.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository setNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setNroOSHeader(123456)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setNroOSNote(123456)
            ).thenReturn(
                resultFailure(
                    "NoteMotoMecRepository.setNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOSCommon -> NoteMotoMecRepository.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setNroOSHeader(123456)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setNroOSNote(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
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