package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Collection
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CollectionRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class IFertigationRepositoryTest {

    private val headerMotoMecSharedPreferencesDatasource = mock<IHeaderMotoMecSharedPreferencesDatasource>()
    private val itemMotoMecSharedPreferencesDatasource = mock<IItemMotoMecSharedPreferencesDatasource>()
    private val collectionRoomDatasource = mock<CollectionRoomDatasource>()
    private val repository = IFertigationRepository(
        headerMotoMecSharedPreferencesDatasource = headerMotoMecSharedPreferencesDatasource,
        itemMotoMecSharedPreferencesDatasource = itemMotoMecSharedPreferencesDatasource,
        collectionRoomDatasource = collectionRoomDatasource
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

    @Test
    fun `getIdNozzle - Check return failure if have error in ItemMotoMecSharedPreferencesDatasource getIdNozzle`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.getIdNozzle()
            ).thenReturn(
                resultFailure(
                    "IItemMotoMecSharedPreferencesDatasource.getIdNozzle",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdNozzle()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.getIdNozzle -> IItemMotoMecSharedPreferencesDatasource.getIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdNozzle - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.getIdNozzle()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdNozzle()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `setValuePressure - Check return failure if have error in ItemMotoMecSharedPreferencesDatasource setValuePressure`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.setValuePressure(10.0)
            ).thenReturn(
                resultFailure(
                    "IItemMotoMecSharedPreferencesDatasource.setValuePressure",
                    "-",
                    Exception()
                )
            )
            val result = repository.setValuePressure(10.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.setValuePressure -> IItemMotoMecSharedPreferencesDatasource.setValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setValuePressure - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setValuePressure(10.0)
            verify(itemMotoMecSharedPreferencesDatasource, atLeastOnce()).setValuePressure(10.0)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `getValuePressure - Check return failure if have error in ItemMotoMecSharedPreferencesDatasource getValuePressure`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.getValuePressure()
            ).thenReturn(
                resultFailure(
                    "IItemMotoMecSharedPreferencesDatasource.getValuePressure",
                    "-",
                    Exception()
                )
            )
            val result = repository.getValuePressure()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.getValuePressure -> IItemMotoMecSharedPreferencesDatasource.getValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getValuePressure - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.getValuePressure()
            ).thenReturn(
                Result.success(10.0)
            )
            val result = repository.getValuePressure()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                10.0
            )
        }

    @Test
    fun `setSpeedPressure - Check return failure if have error in ItemMotoMecSharedPreferencesDatasource setSpeedPressure`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.setSpeedPressure(1)
            ).thenReturn(
                resultFailure(
                    "IItemMotoMecSharedPreferencesDatasource.setSpeedPressure",
                    "-",
                    Exception()
                )
            )
            val result = repository.setSpeedPressure(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.setSpeedPressure -> IItemMotoMecSharedPreferencesDatasource.setSpeedPressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setSpeedPressure - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setSpeedPressure(1)
            verify(itemMotoMecSharedPreferencesDatasource, atLeastOnce()).setSpeedPressure(1)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `initialCollection - Check return failure if have error in CollectionRoomDatasource insert`() =
        runTest {
            val modelCaptor = argumentCaptor<CollectionRoomModel>().apply {
                whenever(
                    collectionRoomDatasource.insert(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "ICollectionRoomDatasource.insert",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.initialCollection(1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.initialCollection -> ICollectionRoomDatasource.insert"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                1
            )
        }

    @Test
    fun `initialCollection - Check return correct if function execute successfully`() =
        runTest {
            val modelCaptor = argumentCaptor<CollectionRoomModel>().apply {
                whenever(
                    collectionRoomDatasource.insert(
                        capture()
                    )
                ).thenReturn(
                    Result.success(Unit)
                )
            }
            val result = repository.initialCollection(1, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                1
            )
        }

    @Test
    fun `hasByIdHeaderAndValueNull - Check return failure if have error in CollectionRoomDatasource hasByIdHeaderAndValueNull`() =
        runTest {
            whenever(
                collectionRoomDatasource.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                resultFailure(
                    "ICollectionRoomDatasource.hasByIdHeaderAndValueNull",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasCollectionByIdHeaderAndValueNull(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.hasCollectionByIdHeaderAndValueNull -> ICollectionRoomDatasource.hasByIdHeaderAndValueNull"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasByIdHeaderAndValueNull - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                collectionRoomDatasource.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasCollectionByIdHeaderAndValueNull(1)
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
    fun `listCollectionByIdHeader - Check return failure if have error in PerformanceRoomDatasource listByIdHeader`() =
        runTest {
            whenever(
                collectionRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.listCollectionByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.listCollectionByIdHeader -> IPerformanceRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                collectionRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        CollectionRoomModel(
                            id = 1,
                            idHeader = 1,
                            nroOS = 1,
                            value = 10.0,
                            dateHour = Date(1750422691)
                        )
                    )
                )
            )
            val result = repository.listCollectionByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Collection(
                        id = 1,
                        idHeader = 1,
                        nroOS = 1,
                        value = 10.0,
                        dateHour = Date(1750422691)
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in CollectionRoomDatasource update`() =
        runTest {
            whenever(
                collectionRoomDatasource.update(1, 50.0)
            ).thenReturn(
                resultFailure(
                    "ICollectionRoomDatasource.update",
                    "-",
                    Exception()
                )
            )
            val result = repository.updateCollection(1, 50.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFertigationRepository.updateCollection -> ICollectionRoomDatasource.update"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `update - Check return correct if function execute successfully`() =
        runTest {
            repository.updateCollection(1, 50.0)
            verify(collectionRoomDatasource, atLeastOnce()).update(1, 50.0)
        }

}