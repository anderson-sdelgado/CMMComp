package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdEquipTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = ISetIdEquip(
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getTypeByIdEquip`() =
        runTest {
            whenever(
                equipRepository.getTypeEquip()
            ).thenReturn(
                resultFailure(
                    "EquipRepository.getTypeByIdEquip",
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
                "ISetIdEquip -> EquipRepository.getTypeByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdEquip`() =
        runTest {
            whenever(
                equipRepository.getTypeEquip()
            ).thenReturn(
                Result.success(TypeEquipMain.NORMAL)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquipMain = TypeEquipMain.NORMAL
                )
            ).thenReturn(
                resultFailure(
                    "HeaderMotoMecRepository.setIdEquip",
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
                "ISetIdEquip -> HeaderMotoMecRepository.setIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and typeEquip is 1`() =
        runTest {
            whenever(
                equipRepository.getTypeEquip()
            ).thenReturn(
                Result.success(TypeEquipMain.NORMAL)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquipMain = TypeEquipMain.NORMAL
                )
            ).thenReturn(
                Result.success(true)
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

    @Test
    fun `Check return correct if function execute successfully and typeEquip is 3`() =
        runTest {
            whenever(
                equipRepository.getTypeEquip()
            ).thenReturn(
                Result.success(TypeEquipMain.NORMAL)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquipMain = TypeEquipMain.FERT
                )
            ).thenReturn(
                Result.success(true)
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