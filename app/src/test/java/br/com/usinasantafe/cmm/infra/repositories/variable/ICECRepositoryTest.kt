package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderPreCEC
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderPreCECSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ICECRepositoryTest {

    private val headerPreCECSharedPreferencesDatasource = mock<HeaderPreCECSharedPreferencesDatasource>()
    private val trailerSharedPreferencesDatasource = mock<TrailerSharedPreferencesDatasource>()
    private val trailerPreCECSharedPreferencesDatasource = mock<TrailerPreCECSharedPreferencesDatasource>()
    private val repository = ICECRepository(
        headerPreCECSharedPreferencesDatasource = headerPreCECSharedPreferencesDatasource,
        trailerSharedPreferencesDatasource = trailerSharedPreferencesDatasource,
        trailerPreCECSharedPreferencesDatasource = trailerPreCECSharedPreferencesDatasource
    )

    @Test
    fun `get - Check return failure if have error in PreCECSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.get()
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
                headerPreCECSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderPreCECSharedPreferencesModel(
                        idRelease = 1,
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
                HeaderPreCEC(
                    idRelease = 1,
                    nroEquip = 2
                )
            )
        }

    @Test
    fun `setDateExitMillHeaderPreCEC - Check return failure if have error in PreCECSharedPreferencesDatasource setDateExitMill`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setDateExitMill(any())
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setDateExitMill",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateExitMillHeaderPreCEC(Date())
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
    fun `setDateExitMillHeaderPreCEC - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setDateExitMill(any())
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setDateExitMillHeaderPreCEC(Date())
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `setDateFieldArrivalHeaderPreCEC - Check return failure if have error in PreCECSharedPreferencesDatasource setDateFieldArrival`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setDateFieldArrival(any())
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setDateFieldArrival",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateFieldArrivalHeaderPreCEC(Date())
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
    fun `setDateFieldArrivalHeaderPreCEC - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setDateFieldArrival(any())
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setDateFieldArrivalHeaderPreCEC(Date())
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `setDateExitArrivalHeaderPreCEC - Check return failure if have error in PreCECSharedPreferencesDatasource setDateExitArrival`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setDateExitField(any())
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setDateExitArrival",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateExitFieldHeaderPreCEC(Date())
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
    fun `setDateExitArrivalHeaderPreCEC - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setDateExitField(any())
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setDateExitFieldHeaderPreCEC(Date())
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `hasCouplingTrailer - Check return failure if have error in TrailerSharedPreferencesDatasource has`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.has()
            ).thenReturn(
                resultFailure(
                    "ITrailerSharedPreferencesDatasource.has",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasCouplingTrailer()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.hasCouplingTrailer -> ITrailerSharedPreferencesDatasource.has"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasCouplingTrailer - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.has()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasCouplingTrailer()
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
    fun `uncouplingTrailer - Check return failure if have error in TrailerSharedPreferencesDatasource clean`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "ITrailerSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.uncouplingTrailer()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.uncouplingTrailer -> ITrailerSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `uncouplingTrailer - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.uncouplingTrailer()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `setDataHeaderPreCEC - Check return failure if have error in PreCECSharedPreferencesDatasource setNroEquip`() =
        runTest {
            whenever(
                headerPreCECSharedPreferencesDatasource.setData(2200, 19759, 2)
            ).thenReturn(
                resultFailure(
                    "IPreCECSharedPreferencesDatasource.setData",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDataHeaderPreCEC(2200, 19759, 2)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setData -> IPreCECSharedPreferencesDatasource.setData"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setDataHeaderPreCEC - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setDataHeaderPreCEC(2200, 19759, 2)
            verify(headerPreCECSharedPreferencesDatasource, atLeastOnce())
                .setData(2200, 19759, 2)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `setIdReleasePreCEC - Check return failure if have error in TrailerPreCECSharedPreferencesDatasource count`() =
        runTest {
            whenever(
                trailerPreCECSharedPreferencesDatasource.count()
            ).thenReturn(
                resultFailure(
                    "ITrailerPreCECSharedPreferencesDatasource.count",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdReleasePreCEC(10_000)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setIdReleasePreCEC -> ITrailerPreCECSharedPreferencesDatasource.count"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdReleasePreCEC - Check return failure if have error in HeaderPreCECSharedPreferencesDatasource setIdRelease and count is 0`() =
        runTest {
            whenever(
                trailerPreCECSharedPreferencesDatasource.count()
            ).thenReturn(
                Result.success(0)
            )
            whenever(
                headerPreCECSharedPreferencesDatasource.setIdRelease(10_000)
            ).thenReturn(
                resultFailure(
                    "IHeaderPreCECSharedPreferencesDatasource.setIdRelease",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdReleasePreCEC(10_000)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setIdReleasePreCEC -> IHeaderPreCECSharedPreferencesDatasource.setIdRelease"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdReleasePreCEC - Check return failure if have error in TrailerPreCECSharedPreferencesDatasource setIdRelease and count is 1`() =
        runTest {
            whenever(
                trailerPreCECSharedPreferencesDatasource.count()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                trailerPreCECSharedPreferencesDatasource.setIdRelease(10_000)
            ).thenReturn(
                resultFailure(
                    "ITrailerPreCECSharedPreferencesDatasource.setIdRelease",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdReleasePreCEC(10_000)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICECRepository.setIdReleasePreCEC -> ITrailerPreCECSharedPreferencesDatasource.setIdRelease"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdReleasePreCEC - Check return false if count is 0`() =
        runTest {
            whenever(
                trailerPreCECSharedPreferencesDatasource.count()
            ).thenReturn(
                Result.success(0)
            )
            val result = repository.setIdReleasePreCEC(10_000)
            verify(headerPreCECSharedPreferencesDatasource, atLeastOnce()).setIdRelease(10_000)
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
    fun `setIdReleasePreCEC - Check return false if count is 1`() =
        runTest {
            whenever(
                trailerPreCECSharedPreferencesDatasource.count()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.setIdReleasePreCEC(10_000)
            verify(trailerPreCECSharedPreferencesDatasource, atLeastOnce()).setIdRelease(10_000)
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
    fun `setIdReleasePreCEC - Check return true if count is 4`() =
        runTest {
            whenever(
                trailerPreCECSharedPreferencesDatasource.count()
            ).thenReturn(
                Result.success(4)
            )
            val result = repository.setIdReleasePreCEC(10_000)
            verify(trailerPreCECSharedPreferencesDatasource, atLeastOnce()).setIdRelease(10_000)
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