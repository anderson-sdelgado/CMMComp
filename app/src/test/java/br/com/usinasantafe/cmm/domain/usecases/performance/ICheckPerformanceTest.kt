package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckPerformanceTest {

    private val osRepository = mock<OSRepository>()
    private val performanceRepository = mock<PerformanceRepository>()
    private val usecase = ICheckPerformance(
        osRepository = osRepository,
        performanceRepository = performanceRepository
    )

    @Test
    fun `Check return failure if value is incorrect`() =
        runTest {
            val result = usecase(1,"as1dfs52fda,5")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPerformance -> stringToDouble"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"as1dfs52fda,5\""
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getNroOSPerformanceById`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNroOSPerformanceById",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1, "50,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPerformance -> IMotoMecRepository.getNroOSPerformanceById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository hasByNroOS`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                Result.success(500)
            )
            whenever(
                osRepository.hasByNroOS(500)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.hasByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1, "50,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPerformance -> IOSRepository.hasByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if value input is less than value 150 and OS not exists in database`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                Result.success(500)
            )
            whenever(
                osRepository.hasByNroOS(500)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase(1, "50,0")
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
    fun `Check return false if value input is greater than value 150 and OS not exists in database`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                Result.success(500)
            )
            whenever(
                osRepository.hasByNroOS(500)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase(1, "250,0")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository getByNroOS and OS exists in database`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                Result.success(500)
            )
            whenever(
                osRepository.hasByNroOS(500)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(500)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.getByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1, "50,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPerformance -> IOSRepository.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if value input is less than value BD and OS exists in database`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                Result.success(500)
            )
            whenever(
                osRepository.hasByNroOS(500)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(500)
            ).thenReturn(
                Result.success(
                    OS(
                        idOS = 1,
                        nroOS = 123456,
                        idEquip = 1,
                        idLibOS = 1,
                        idPropAgr = 1,
                        areaOS = 100.0
                    )
                )
            )
            val result = usecase(1, "50,0")
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
    fun `Check return false if value input is greater than value BD and OS exists in database`() =
        runTest {
            whenever(
                performanceRepository.getNroOSById(1)
            ).thenReturn(
                Result.success(500)
            )
            whenever(
                osRepository.hasByNroOS(500)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(500)
            ).thenReturn(
                Result.success(
                    OS(
                        idOS = 1,
                        nroOS = 123456,
                        idEquip = 1,
                        idLibOS = 1,
                        idPropAgr = 1,
                        areaOS = 100.0
                    )
                )
            )
            val result = usecase(1, "250,0")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}