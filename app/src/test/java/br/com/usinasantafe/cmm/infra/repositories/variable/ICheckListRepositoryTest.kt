package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckListRepositoryTest {

    private val headerCheckListSharedPreferencesDatasource = mock<HeaderCheckListSharedPreferencesDatasource>()
    private val repository = ICheckListRepository(
        headerCheckListSharedPreferencesDatasource = headerCheckListSharedPreferencesDatasource
    )

    @Test
    fun `saveHeader - Check return failure if have error in HeaderCheckListSharedPreferencesDatasource clean`() =
        runTest {
            whenever(
                headerCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveHeader(
                HeaderCheckList(
                    regOperator = 1,
                    nroTurn = 1,
                    nroEquip = 1,
                    dateHour = Date()
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveHeader -> IHeaderCheckListSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveHeader - Check return failure if have error in HeaderCheckListSharedPreferencesDatasource save`() =
        runTest {
            val entity = HeaderCheckList(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date()
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.save(entity.entityToSharedPreferencesModel())
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveHeader(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveHeader -> IHeaderCheckListSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveHeader - Check return correct if function execute successfully`() =
        runTest {
            val entity = HeaderCheckList(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date()
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.save(entity.entityToSharedPreferencesModel())
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveHeader(entity)
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