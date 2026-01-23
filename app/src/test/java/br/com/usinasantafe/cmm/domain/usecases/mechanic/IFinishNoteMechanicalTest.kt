package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IFinishNoteMechanicalTest {

    private val mechanicRepository = mock<MechanicRepository>()
    private val usecase = IFinishNoteMechanic(
        mechanicRepository = mechanicRepository
    )

    @Test
    fun `Check return failure if have error in MechanicRepository setFinishNote`() =
        runTest {
            whenever(
                mechanicRepository.setFinishNote()
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.setFinishNote",
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
                "IFinishNoteMechanic -> IMechanicRepository.setFinishNote"
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
                mechanicRepository.setFinishNote()
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