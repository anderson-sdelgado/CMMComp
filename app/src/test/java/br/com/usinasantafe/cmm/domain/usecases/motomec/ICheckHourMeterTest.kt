package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckHourMeterTest {

    private val equipRepository = mock<EquipRepository>()
    private val usecase = ICheckHourMeter(
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getHourMeter`() =
        runTest {
            whenever(
                equipRepository.getHourMeter()
            ).thenReturn(
                resultFailure(
                    context = "EquipRepository.getHourMeter",
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
                "ICheckHourMeter -> EquipRepository.getHourMeter"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if hourMeter database is bigger than hourMeter input`() =
        runTest {
            whenever(
                equipRepository.getHourMeter()
            ).thenReturn(
                Result.success(20000.0)
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.hourMeterBD,
                "20.000,0"
            )
            assertEquals(
                entity.check,
                false
            )
        }

    @Test
    fun `Check return true if hourMeter database is less than hourMeter input`() =
        runTest {
            whenever(
                equipRepository.getHourMeter()
            ).thenReturn(
                Result.success(5000.0)
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.hourMeterBD,
                "5.000,0"
            )
            assertEquals(
                entity.check,
                true
            )
        }
}