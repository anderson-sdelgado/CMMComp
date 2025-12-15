package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckTypeTranshipmentInActivityTest {

    private val functionActivityRepository = mock<FunctionActivityRepository>()
    private val usecase = ICheckTypeTranshipmentInActivity(
        functionActivityRepository = functionActivityRepository
    )

    @Test
    fun `Check return failure if have error in FunctionActivityRepository listByIdActivity`() =
        runTest {
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.listByIdActivity",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckTypeTranshipmentInActivity -> IFunctionActivityRepository.listByIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if return emptyList in FunctionActivityRepository listByIdActivity`() =
        runTest {
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return false if not have TypeActivity TRANSHIPMENT in functionActivity by idActivity`() =
        runTest {
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.REEL,
                        )
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return true if have TypeActivity TRANSHIPMENT in functionActivity by idActivity`() =
        runTest {
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.REEL,
                        ),
                        FunctionActivity(
                            idFunctionActivity = 2,
                            idActivity = 1,
                            typeActivity = TypeActivity.TRANSHIPMENT,
                        )
                    )
                )
            )
            val result = usecase(1)
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