package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IFertigationRepositoryTest {

    private val headerMotoMecSharedPreferencesDatasource = mock<IHeaderMotoMecSharedPreferencesDatasource>()
    private val itemMotoMecSharedPreferencesDatasource = mock<IItemMotoMecSharedPreferencesDatasource>()
    private val repository = IFertigationRepository(
        headerMotoMecSharedPreferencesDatasource = headerMotoMecSharedPreferencesDatasource,
        itemMotoMecSharedPreferencesDatasource = itemMotoMecSharedPreferencesDatasource
    )

    @Test
    fun `setIdEquipMotorPump - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource setIdEquipMotorPump`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(10)
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecSharedPreferencesDatasource.setIdEquipMotorPump",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdEquipMotorPump(10)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.setIdEquipMotorPump -> IHeaderMotoMecSharedPreferencesDatasource.setIdEquipMotorPump"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdEquipMotorPump - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setIdEquipMotorPump(10)
            verify(headerMotoMecSharedPreferencesDatasource, atLeastOnce()).setIdEquipMotorPump(10)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `setIdNozzle - Check return failure if have error in ItemMotoMecSharedPreferencesDatasource setIdNozzle`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.setIdNozzle(1)
            ).thenReturn(
                resultFailure(
                    "IItemMotoMecSharedPreferencesDatasource.setIdNozzle",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdNozzle(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.setIdNozzle -> IItemMotoMecSharedPreferencesDatasource.setIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdNozzle - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setIdNozzle(1)
            verify(itemMotoMecSharedPreferencesDatasource, atLeastOnce()).setIdNozzle(1)
            assertEquals(
                result.isSuccess,
                true
            )
        }


}