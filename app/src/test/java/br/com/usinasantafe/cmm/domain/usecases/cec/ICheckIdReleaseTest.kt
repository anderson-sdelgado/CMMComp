package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckIdReleaseTest {

    private val osRepository = mock<OSRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ICheckIdRelease(
        osRepository = osRepository,
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if value input is incorrect`() =
        runTest {
            val result = usecase("dsfsda452")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRelease -> toInt"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"dsfsda452\""
            )
        }
    
    @Test
    fun `Check return failure if have error in MotoMecRepository getNroOSHeader`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNroOSHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                "123456"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRelease -> IMotoMecRepository.getNroOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return failure if have error in OSRepository hasByNroOSAndIdRelease`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(10000)
            )
            whenever(
                osRepository.hasByNroAndIdRelease(10000, 123456)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.hasByNroOSAndIdRelease",
                    "-",
                    Exception()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRelease -> IOSRepository.hasByNroOSAndIdRelease"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(10000)
            )
            whenever(
                osRepository.hasByNroAndIdRelease(10000, 123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("123456")
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