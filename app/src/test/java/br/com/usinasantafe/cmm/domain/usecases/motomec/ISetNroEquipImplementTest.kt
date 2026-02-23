package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.variable.Implement
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.implement.ISetNroEquipImplement
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNroEquipImplementTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ISetNroEquipImplement(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if value of field is incorrect`() =
        runTest {
            val result = usecase(
                nroEquip = "200a",
                pos = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipImplement -> toLong"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }


    @Test
    fun `Check return failure if have error in MotoMecRepository setNroEquipImplement`() =
        runTest {
            whenever(
                motoMecRepository.setNroEquipImplement(
                    Implement(
                        nroEquip = 2200,
                        pos = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setNroEquipImplement",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "2200",
                pos = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipImplement -> IMotoMecRepository.setNroEquipImplement"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase("2200", 1)
            verify(motoMecRepository, atLeastOnce()).setNroEquipImplement(
                Implement(
                    nroEquip = 2200,
                    pos = 1
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
        }

}