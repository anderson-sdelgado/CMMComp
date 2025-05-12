package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IHeaderMotoMecRepositoryTest {

    private val headerMotoMecSharedPreferencesDatasource = mock<HeaderMotoMecSharedPreferencesDatasource>()
    private val usecase = IHeaderMotoMecRepository(
        headerMotoMecSharedPreferencesDatasource = headerMotoMecSharedPreferencesDatasource
    )

    @Test
    fun `setRegOperator - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setRegOperator",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setRegOperator(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setRegOperator -> IHeaderMotoMecSharedPreferencesDatasource.setRegOperator"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setRegOperator - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setRegOperator(12345)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                true
            )
        }

    @Test
    fun `setIdEquip - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource setIdEquip`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquip(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setIdEquip(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.setIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquip(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setIdEquip(12345)
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
    fun `setIdTurn - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource setIdTurn`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdTurn(1)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdTurn",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setIdTurn(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setIdTurn -> IHeaderMotoMecSharedPreferencesDatasource.setIdTurn"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdTurn - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdTurn(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setIdTurn(1)
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
    fun `setNroOS - Check return failure if have error in IHeaderMotoMecRepository setNroOS`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setNroOS(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setNroOS(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setNroOS -> IHeaderMotoMecSharedPreferencesDatasource.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setNroOS(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setNroOS(12345)
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