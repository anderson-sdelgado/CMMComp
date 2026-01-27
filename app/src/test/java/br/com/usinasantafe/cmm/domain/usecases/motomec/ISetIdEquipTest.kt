package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetIdEquipTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = ISetIdEquip(
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getIdEquipMain`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdEquipMain",
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
                "ISetIdEquip -> IEquipRepository.getIdEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getTypeByIdEquip`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                equipRepository.getTypeEquipMain()
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
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                equipRepository.getTypeEquipMain()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL
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
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                equipRepository.getTypeEquipMain()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `Check return correct if function execute successfully and typeEquip is 3`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                equipRepository.getTypeEquipMain()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquip = TypeEquip.REEL_FERT
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }
}