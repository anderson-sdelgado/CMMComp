package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ISetCheckUpdateAllTableTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISetCheckUpdateAllTable(
        configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository setFlagUpdate`() =
        runTest {
            whenever(
                configRepository.setFlagUpdate(
                    FlagUpdate.UPDATED
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(FlagUpdate.UPDATED)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetCheckUpdateAllTable -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause,
                null
            )
        }

    @Test
    fun `Chech return true if usecase is success`() =
        runTest {
            whenever(
                configRepository.setFlagUpdate(
                    FlagUpdate.UPDATED
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(FlagUpdate.UPDATED)
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