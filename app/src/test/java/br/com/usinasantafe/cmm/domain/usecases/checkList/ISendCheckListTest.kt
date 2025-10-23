package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISendCheckListTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val getToken = mock<GetToken>()
    private val usecase = ISendCheckList(
        checkListRepository = checkListRepository,
        configRepository = configRepository,
        getToken = getToken
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getNumber`() =
        runTest {
            whenever(
                configRepository.getNumber()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getNumber",
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
                "ISendCheckList -> IConfigRepository.getNumber"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                configRepository.getNumber()
            ).thenReturn(
                Result.success(16997417840)
            )
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "IGetToken",
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
                "ISendCheckList -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository send`() =
        runTest {
            whenever(
                configRepository.getNumber()
            ).thenReturn(
                Result.success(16997417840)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("TOKEN")
            )
            whenever(
                checkListRepository.send(
                    number = 16997417840,
                    token = "TOKEN"
                )
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.send",
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
                "ISendCheckList -> ICheckListRepository.send"
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
                configRepository.getNumber()
            ).thenReturn(
                Result.success(16997417840)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("TOKEN")
            )
            whenever(
                checkListRepository.send(
                    number = 16997417840,
                    token = "TOKEN"
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