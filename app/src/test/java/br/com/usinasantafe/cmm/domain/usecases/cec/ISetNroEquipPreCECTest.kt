package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNroEquipPreCECTest {

    private val equipRepository = mock<EquipRepository>()
    private val cecRepository = mock<CECRepository>()
    private val usecase = ISetNroEquipPreCEC(
        equipRepository = equipRepository,
        cecRepository = cecRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getNroEquipMain`() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getNroEquipMain",
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
                "ISetNroEquipPreCEC -> IEquipRepository.getNroEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CECRepository setNroEquip`() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(2200)
            )
            whenever(
                cecRepository.setNroEquip(2200)
            ).thenReturn(
                resultFailure(
                    "ICECRepository.setNroEquip",
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
                "ISetNroEquipPreCEC -> ICECRepository.setNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getCodClass`() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(2200)
            )
            whenever(
                equipRepository.getCodClass()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getCodClass",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            verify(cecRepository, atLeastOnce()).setNroEquip(2200)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipPreCEC -> IEquipRepository.getCodClass"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if function execute successfully and EquipRepository getCodClass return 1`() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(2200)
            )
            whenever(
                equipRepository.getCodClass()
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase()
            verify(cecRepository, atLeastOnce()).setNroEquip(2200)
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
    fun `Check return false if function execute successfully and EquipRepository getCodClass return 2`() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(2200)
            )
            whenever(
                equipRepository.getCodClass()
            ).thenReturn(
                Result.success(2)
            )
            val result = usecase()
            verify(cecRepository, atLeastOnce()).setNroEquip(2200)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}