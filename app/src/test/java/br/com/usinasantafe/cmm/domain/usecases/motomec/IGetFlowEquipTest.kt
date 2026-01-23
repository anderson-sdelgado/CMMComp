package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowEquipNote
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IGetFlowEquipTest {

    private val configRepository = mock<ConfigRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetFlowEquip(
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getIdEquip`() =
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
                "IGetFlowEquip -> IEquipRepository.getIdEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdEquipHeader`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdEquipHeader",
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
                "IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return MAIN if idEquip Config and idEquipHeader MotoMec are equals`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowEquipNote.MAIN
            )
        }

    @Test
    fun `Check return SECONDARY if idEquip Config and idEquipHeader MotoMec are not equals`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(2)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowEquipNote.SECONDARY
            )
        }

}