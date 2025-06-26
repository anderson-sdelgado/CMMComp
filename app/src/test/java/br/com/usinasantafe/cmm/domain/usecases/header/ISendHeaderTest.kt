package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISendHeaderTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val getToken = mock<GetToken>()
    private val usecase = ISendHeader(
        motoMecRepository = motoMecRepository,
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
                    context = "ConfigRepository.getNumber",
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
                "ISendHeader -> ConfigRepository.getNumber"
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
                Result.success(12345)
            )
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    context = "GetToken",
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
                "ISendHeader -> GetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return failure if have error in MotoMecRepository send`() =
        runTest {
            whenever(
                configRepository.getNumber()
            ).thenReturn(
                Result.success(12345)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                motoMecRepository.send(
                    number = 12345,
                    token = "token"
                )
            ).thenReturn(
                resultFailure(
                    context = "MotoMecRepository.send",
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
                "ISendHeader -> MotoMecRepository.send"
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
                Result.success(12345)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                motoMecRepository.send(
                    number = 12345,
                    token = "token"
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