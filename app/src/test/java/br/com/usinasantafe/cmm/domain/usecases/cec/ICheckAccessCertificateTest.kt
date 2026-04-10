package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderPreCEC
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckAccessCertificateTest {

    private val cecRepository = mock<CECRepository>()
    private val usecase = ICheckAccessCertificate(
        cecRepository = cecRepository
    )

    @Test
    fun `Check return failure if have error in CECRepository get`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                resultFailure(
                    "ICECRepository.get",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAccessCertificate -> ICECRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if function execute successfully and dateExitMill is null`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    HeaderPreCEC()
                )
            )
            val result = usecase()
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
    fun `Check return false if function execute successfully and dateExitMill is not null and dateFieldArrival is null`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    HeaderPreCEC(
                        dateExitMill = Date(),
                    )
                )
            )
            val result = usecase()
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
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    HeaderPreCEC(
                        dateExitMill = Date(),
                        dateFieldArrival = Date()
                    )
                )
            )
            val result = usecase()
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