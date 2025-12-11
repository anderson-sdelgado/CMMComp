package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
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
    fun `Check return failure if have error in EquipRepository getMeasureByIdEquip`() =
        runTest {
            whenever(
                equipRepository.getHourMeter()
            ).thenReturn(
                resultFailure(
                    context = "EquipRepository.getMeasureByIdEquip",
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
                "ICheckHourMeter -> EquipRepository.getMeasureByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if measure database is bigger than measure input`() =
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
                entity.measureBD,
                "20.000,0"
            )
            assertEquals(
                entity.check,
                false
            )
        }

    @Test
    fun `Check return true if measure database is less than measure input`() =
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
                entity.measureBD,
                "5.000,0"
            )
            assertEquals(
                entity.check,
                true
            )
        }
}