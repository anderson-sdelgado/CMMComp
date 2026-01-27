package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IHasEquipSecondaryTest {

    private val equipRepository = mock<EquipRepository>()
    private val usecase = IHasEquipSecondary(
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if nroEquip is not Long`() =
        runTest {
            val result = usecase(
                nroEquip = "2200aL",
                typeEquip = TypeEquip.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasEquipSecondary -> toLong"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"2200aL\""
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository hasEquipSecondary`() =
        runTest {
            whenever(
                equipRepository.hasEquipSecondary(
                    nroEquip = 2200,
                    typeEquip = TypeEquip.TRANSHIPMENT
                )
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.hasEquipSecondary",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "2200",
                typeEquip = TypeEquip.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasEquipSecondary -> IEquipRepository.hasEquipSecondary"
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
                equipRepository.hasEquipSecondary(
                    nroEquip = 2200,
                    typeEquip = TypeEquip.TRANSHIPMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroEquip = "2200",
                typeEquip = TypeEquip.TRANSHIPMENT
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