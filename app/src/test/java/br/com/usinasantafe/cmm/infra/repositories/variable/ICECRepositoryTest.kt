package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.PreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.PreCECSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ICECRepositoryTest {

    private val preCECSharedPreferencesDatasource = mock<PreCECSharedPreferencesDatasource>()
    private val repository = ICECRepository(
        preCECSharedPreferencesDatasource = preCECSharedPreferencesDatasource
    )

    @Test
    fun `get - Check return failure if have error in PreCECSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.get -> IPreCECSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    PreCECSharedPreferencesModel(
                        libEquip = 1,
                        nroEquip = 2
                    )
                )
            )
            val result = repository.get()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                PreCEC(
                    libEquip = 1,
                    nroEquip = 2
                )
            )
        }

    @Test
    fun `setDateExitMill - Check return failure if have error in PreCECSharedPreferencesDatasource setDateExitMill`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.setDateExitMill(any())
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setDateExitMill",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateExitMill(Date())
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setDateExitMill -> IPreCECSharedPreferencesDatasource.setDateExitMill"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setDateExitMill - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.setDateExitMill(any())
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDateExitMill(Date())
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
    fun `setDateFieldArrival - Check return failure if have error in PreCECSharedPreferencesDatasource setDateFieldArrival`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.setDateFieldArrival(any())
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setDateFieldArrival",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateFieldArrival(Date())
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setDateFieldArrival -> IPreCECSharedPreferencesDatasource.setDateFieldArrival"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setDateFieldArrival - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.setDateFieldArrival(any())
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDateFieldArrival(Date())
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
    fun `setDateExitArrival - Check return failure if have error in PreCECSharedPreferencesDatasource setDateExitArrival`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.setDateExitField(any())
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setDateExitArrival",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateExitField(Date())
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setDateExitField -> IPreCECSharedPreferencesDatasource.setDateExitArrival"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setDateExitArrival - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                preCECSharedPreferencesDatasource.setDateExitField(any())
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDateExitField(Date())
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