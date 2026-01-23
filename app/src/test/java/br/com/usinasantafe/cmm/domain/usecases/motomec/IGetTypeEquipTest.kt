package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetTypeEquipTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = IGetTypeEquip(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository getTypeEquipHeader`() =
        runTest {
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                resultFailure(
                    context = "MotoMecRepository.getTypeEquipHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetTypeEquip -> MotoMecRepository.getTypeEquipHeader"
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
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeEquip.NORMAL
            )
        }

}