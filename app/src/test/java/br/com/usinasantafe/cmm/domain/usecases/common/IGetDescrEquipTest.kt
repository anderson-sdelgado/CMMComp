package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.motomec.IGetDescrEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetDescrEquipTest {

    private val equipRepository = mock<EquipRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = IGetDescrEquip(
        equipRepository = equipRepository,
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdEquipHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    "MotoMecRepository.getIdEquipHeader",
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
                "IGetDescrEquip -> MotoMecRepository.getIdEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return failure if have error in EquipRepository getDescr`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getDescrByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRepository.getDescr",
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
                "IGetDescrEquip -> EquipRepository.getDescr"
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
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getDescrByIdEquip(1)
            ).thenReturn(
                Result.success("200 - TRATOR")
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "200 - TRATOR"
            )
        }

}