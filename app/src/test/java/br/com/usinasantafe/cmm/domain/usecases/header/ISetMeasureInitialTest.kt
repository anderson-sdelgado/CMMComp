package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetMeasureInitialTest {

    private val headerMotoMecRepository = mock<HeaderMotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val saveHeaderOpen = mock<SaveHeaderOpen>()
    private val usecase = ISetMeasureInitial(
        headerMotoMecRepository = headerMotoMecRepository,
        equipRepository = equipRepository,
        saveHeaderOpen = saveHeaderOpen
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setMeasureInitial`() =
        runTest {
            whenever(
                headerMotoMecRepository.setMeasureInitial(
                    measure = 10000.0
                )
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.setMeasureInitial",
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
                "ISetMeasureInitial -> IHeaderMotoMecRepository.setMeasureInitial"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getIdEquip`() =
        runTest {
            whenever(
                headerMotoMecRepository.setMeasureInitial(
                    measure = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdEquip()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRepository.getIdEquip",
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
                "ISetMeasureInitial -> IHeaderMotoMecRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository updateMeasureByIdEquip`() =
        runTest {
            whenever(
                headerMotoMecRepository.setMeasureInitial(
                    measure = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateMeasureByIdEquip(
                    measure = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IEquipRepository.updateMeasureByIdEquip",
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
                "ISetMeasureInitial -> IEquipRepository.updateMeasureByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in SaveHeaderOpen`() =
        runTest {
            whenever(
                headerMotoMecRepository.setMeasureInitial(
                    measure = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateMeasureByIdEquip(
                    measure = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveHeaderOpen()
            ).thenReturn(
                resultFailure(
                    context = "ISaveHeaderOpen",
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
                "ISetMeasureInitial -> ISaveHeaderOpen"
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
                headerMotoMecRepository.setMeasureInitial(
                    measure = 10000.0
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.updateMeasureByIdEquip(
                    measure = 10000.0,
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveHeaderOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("10.000,0")
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