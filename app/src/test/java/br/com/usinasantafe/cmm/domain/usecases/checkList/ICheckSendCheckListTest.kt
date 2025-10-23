package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckSendCheckListTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ICheckSendCheckList(
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository checkSend`() =
        runTest {
            whenever(
                checkListRepository.checkSend()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.checkSend",
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
                "ICheckSendCheckList -> ICheckListRepository.checkSend"
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
                checkListRepository.checkSend()
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