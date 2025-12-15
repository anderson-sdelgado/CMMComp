package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.ItemRespCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.CheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemRespCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemRespCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.RespItemCheckListRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckListRepositoryTest {

    private val headerCheckListSharedPreferencesDatasource = mock<HeaderCheckListSharedPreferencesDatasource>()
    private val itemRespCheckListSharedPreferencesDatasource = mock<ItemRespCheckListSharedPreferencesDatasource>()
    private val headerCheckListRoomDatasource = mock<HeaderCheckListRoomDatasource>()
    private val itemRespCheckListRoomDatasource = mock<ItemRespCheckListRoomDatasource>()
    private val checkListRetrofitDatasource = mock<CheckListRetrofitDatasource>()
    private val repository = ICheckListRepository(
        headerCheckListSharedPreferencesDatasource = headerCheckListSharedPreferencesDatasource,
        itemRespCheckListSharedPreferencesDatasource = itemRespCheckListSharedPreferencesDatasource,
        headerCheckListRoomDatasource = headerCheckListRoomDatasource,
        itemRespCheckListRoomDatasource = itemRespCheckListRoomDatasource,
        checkListRetrofitDatasource = checkListRetrofitDatasource
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

    @Test
    fun `saveResp - Check return failure if have error in RespItemCheckListSharedPreferencesDatasource save`() =
        runTest {
            val entity = ItemRespCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            val model = RespItemCheckListSharedPreferencesModel(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.add(model)
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveResp(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveResp -> IRespItemCheckListSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveResp - Check return correct if function execute successfully`() =
        runTest {
            val entity = ItemRespCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            val model = RespItemCheckListSharedPreferencesModel(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.add(model)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveResp(entity)
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
    fun `clearResp - Check return failure if have error in RespItemCheckListSharedPreferencesDatasource clean`() =
        runTest {
            whenever(
                itemRespCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.cleanResp()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.clearResp -> IRespItemCheckListSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `clearResp - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemRespCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.cleanResp()
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
    fun `saveCheckList - Check return failure if have error in HeaderCheckListSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveCheckList -> IHeaderCheckListSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveCheckList - Check return failure if have error in HeaderCheckListRoomDatasource save`() =
        runTest {
            val headerSharedPreferencesModel = HeaderCheckListSharedPreferencesModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val headerCheckListRoomModel = HeaderCheckListRoomModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(headerSharedPreferencesModel)
            )
            whenever(
                headerCheckListRoomDatasource.save(headerCheckListRoomModel)
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListRoomDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveCheckList -> IHeaderCheckListRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveCheckList - Check return failure if have error in RespItemCheckListSharedPreferencesDatasource list`() =
        runTest {
            val headerSharedPreferencesModel = HeaderCheckListSharedPreferencesModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val headerCheckListRoomModel = HeaderCheckListRoomModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(headerSharedPreferencesModel)
            )
            whenever(
                headerCheckListRoomDatasource.save(headerCheckListRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.list()
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListSharedPreferencesDatasource.list",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveCheckList -> IRespItemCheckListSharedPreferencesDatasource.list"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveCheckList - Check return failure if have error in RespItemCheckListRoomDatasource save`() =
        runTest {
            val headerSharedPreferencesModel = HeaderCheckListSharedPreferencesModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val headerCheckListRoomModel = HeaderCheckListRoomModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val respItemCheckListSharedPreferencesModelList = listOf(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                ),
                RespItemCheckListSharedPreferencesModel(
                    idItem = 2,
                    option = OptionRespCheckList.REPAIR
                )
            )
            val itemRespCheckListRoomModel = ItemRespCheckListRoomModel(
                idHeader = 1,
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(headerSharedPreferencesModel)
            )
            whenever(
                headerCheckListRoomDatasource.save(headerCheckListRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(respItemCheckListSharedPreferencesModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.save(itemRespCheckListRoomModel)
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListRoomDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveCheckList -> IRespItemCheckListRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveCheckList - Check return failure if have error in HeaderCheckListSharedPreferencesDatasource clean`() =
        runTest {
            val headerSharedPreferencesModel = HeaderCheckListSharedPreferencesModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val headerCheckListRoomModel = HeaderCheckListRoomModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val respItemCheckListSharedPreferencesModelList = listOf(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                ),
                RespItemCheckListSharedPreferencesModel(
                    idItem = 2,
                    option = OptionRespCheckList.REPAIR
                )
            )
            val itemRespCheckListRoomModel = ItemRespCheckListRoomModel(
                idHeader = 1,
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(headerSharedPreferencesModel)
            )
            whenever(
                headerCheckListRoomDatasource.save(headerCheckListRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(respItemCheckListSharedPreferencesModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.save(itemRespCheckListRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveCheckList -> IHeaderCheckListSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveCheckList - Check return failure if have error in RespItemCheckListSharedPreferencesDatasource clean`() =
        runTest {
            val headerSharedPreferencesModel = HeaderCheckListSharedPreferencesModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val headerCheckListRoomModel = HeaderCheckListRoomModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val respItemCheckListSharedPreferencesModelList = listOf(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                ),
                RespItemCheckListSharedPreferencesModel(
                    idItem = 2,
                    option = OptionRespCheckList.REPAIR
                )
            )
            val itemRespCheckListRoomModel = ItemRespCheckListRoomModel(
                idHeader = 1,
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(headerSharedPreferencesModel)
            )
            whenever(
                headerCheckListRoomDatasource.save(headerCheckListRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(respItemCheckListSharedPreferencesModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.save(itemRespCheckListRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveCheckList -> IRespItemCheckListSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveCheckList - Check return correct if function execute successfully`() =
        runTest {
            val headerSharedPreferencesModel = HeaderCheckListSharedPreferencesModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val headerCheckListRoomModel = HeaderCheckListRoomModel(
                regOperator = 1,
                nroTurn = 1,
                nroEquip = 1,
                dateHour = Date(1760708211)
            )
            val respItemCheckListSharedPreferencesModelList = listOf(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                ),
                RespItemCheckListSharedPreferencesModel(
                    idItem = 2,
                    option = OptionRespCheckList.REPAIR
                )
            )
            val itemRespCheckListRoomModel = ItemRespCheckListRoomModel(
                idHeader = 1,
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(headerSharedPreferencesModel)
            )
            whenever(
                headerCheckListRoomDatasource.save(headerCheckListRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(respItemCheckListSharedPreferencesModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.save(itemRespCheckListRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemRespCheckListSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveCheckList()
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
    fun `delLastRespItem - Check return failure if have error in RespItemCheckListSharedPreferencesDatasource delLast`() =
        runTest {
            whenever(
                itemRespCheckListSharedPreferencesDatasource.delLast()
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListSharedPreferencesDatasource.delLast",
                    "-",
                    Exception()
                )
            )
            val result = repository.delLastRespItem()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.delLastRespItem -> IRespItemCheckListSharedPreferencesDatasource.delLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `delLastRespItem - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemRespCheckListSharedPreferencesDatasource.delLast()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delLastRespItem()
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
    fun `checkOpen - Check return failure if have error in HeaderCheckListSharedPreferencesDatasource checkOpen`() =
        runTest {
            whenever(
                headerCheckListSharedPreferencesDatasource.checkOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListSharedPreferencesDatasource.checkOpen",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.checkOpen -> IHeaderCheckListSharedPreferencesDatasource.checkOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerCheckListSharedPreferencesDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkOpen()
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
    fun `checkSend - Check return failure if have error in HeaderCheckListRoomDatasource checkSend`() =
        runTest {
            whenever(
                headerCheckListRoomDatasource.hasSend()
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListRoomDatasource.checkSend",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.checkSend -> IHeaderCheckListRoomDatasource.checkSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerCheckListRoomDatasource.hasSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasSend()
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
    fun `send - Check return failure if have error in HeaderCheckListRoomDatasource listBySend`() =
        runTest {
            whenever(
                headerCheckListRoomDatasource.listBySend()
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListRoomDatasource.listBySend",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.send -> IHeaderCheckListRoomDatasource.listBySend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in RespItemCheckListRoomDatasource listByIdHeader`() =
        runTest {
            val headerCheckListRoomModelList = listOf(
                HeaderCheckListRoomModel(
                    id = 1,
                    nroEquip = 2200,
                    regOperator = 19759,
                    nroTurn = 2,
                    dateHour = Date(1750422691)
                )
            )
            whenever(
                headerCheckListRoomDatasource.listBySend()
            ).thenReturn(
                Result.success(headerCheckListRoomModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.send -> IRespItemCheckListRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in CheckListRetrofitDatasource send`() =
        runTest {
            val headerCheckListRoomModelList = listOf(
                HeaderCheckListRoomModel(
                    id = 1,
                    nroEquip = 2200,
                    regOperator = 19759,
                    nroTurn = 2,
                    dateHour = Date(1750422691000)
                )
            )
            val itemRespCheckListRoomModelList = listOf(
                ItemRespCheckListRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            )
            val headerCheckListRetrofitModelOutputList =
                headerCheckListRoomModelList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        respItemList = itemRespCheckListRoomModelList.map { respItem -> respItem.roomModelToRetrofitModel() }
                    )
                }
            whenever(
                headerCheckListRoomDatasource.listBySend()
            ).thenReturn(
                Result.success(headerCheckListRoomModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(itemRespCheckListRoomModelList)
            )
            whenever(
                checkListRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = headerCheckListRetrofitModelOutputList
                )
            ).thenReturn(
                resultFailure(
                    "ICheckListRetrofitDatasource.send",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.send -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in RespItemCheckListRoomDatasource setIdServById`() =
        runTest {
            val headerCheckListRoomModelList = listOf(
                HeaderCheckListRoomModel(
                    id = 1,
                    nroEquip = 2200,
                    regOperator = 19759,
                    nroTurn = 2,
                    dateHour = Date(1750422691000)
                )
            )
            val itemRespCheckListRoomModelList = listOf(
                ItemRespCheckListRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            )
            val headerCheckListRetrofitModelOutputList =
                headerCheckListRoomModelList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        respItemList = itemRespCheckListRoomModelList.map { respItem -> respItem.roomModelToRetrofitModel() }
                    )
                }
            val headerCheckListRetrofitModelInputList = listOf(
                HeaderCheckListRetrofitModelInput(
                    id = 1,
                    idServ = 1,
                    respItemList = listOf(
                        RespItemCheckListRetrofitModelInput(
                            id = 1,
                            idServ = 1,
                        )
                    )
                )
            )
            whenever(
                headerCheckListRoomDatasource.listBySend()
            ).thenReturn(
                Result.success(headerCheckListRoomModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(itemRespCheckListRoomModelList)
            )
            whenever(
                checkListRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = headerCheckListRetrofitModelOutputList
                )
            ).thenReturn(
                Result.success(headerCheckListRetrofitModelInputList)
            )
            whenever(
                itemRespCheckListRoomDatasource.setIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                resultFailure(
                    "IRespItemCheckListRoomDatasource.setIdServById",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.send -> IRespItemCheckListRoomDatasource.setIdServById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in HeaderCheckListRoomDatasource setSendAndIdServById`() =
        runTest {
            val headerCheckListRoomModelList = listOf(
                HeaderCheckListRoomModel(
                    id = 1,
                    nroEquip = 2200,
                    regOperator = 19759,
                    nroTurn = 2,
                    dateHour = Date(1750422691000)
                )
            )
            val itemRespCheckListRoomModelList = listOf(
                ItemRespCheckListRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            )
            val headerCheckListRetrofitModelOutputList =
                headerCheckListRoomModelList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        respItemList = itemRespCheckListRoomModelList.map { respItem -> respItem.roomModelToRetrofitModel() }
                    )
                }
            val headerCheckListRetrofitModelInputList = listOf(
                HeaderCheckListRetrofitModelInput(
                    id = 1,
                    idServ = 1,
                    respItemList = listOf(
                        RespItemCheckListRetrofitModelInput(
                            id = 1,
                            idServ = 1,
                        )
                    )
                )
            )
            whenever(
                headerCheckListRoomDatasource.listBySend()
            ).thenReturn(
                Result.success(headerCheckListRoomModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(itemRespCheckListRoomModelList)
            )
            whenever(
                checkListRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = headerCheckListRetrofitModelOutputList
                )
            ).thenReturn(
                Result.success(headerCheckListRetrofitModelInputList)
            )
            whenever(
                itemRespCheckListRoomDatasource.setIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListRoomDatasource.setSentAndIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderCheckListRoomDatasource.setSendAndIdServById",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.send -> IHeaderCheckListRoomDatasource.setSendAndIdServById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val headerCheckListRoomModelList = listOf(
                HeaderCheckListRoomModel(
                    id = 1,
                    nroEquip = 2200,
                    regOperator = 19759,
                    nroTurn = 2,
                    dateHour = Date(1750422691000)
                )
            )
            val itemRespCheckListRoomModelList = listOf(
                ItemRespCheckListRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            )
            val headerCheckListRetrofitModelOutputList =
                headerCheckListRoomModelList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        respItemList = itemRespCheckListRoomModelList.map { respItem -> respItem.roomModelToRetrofitModel() }
                    )
                }
            val headerCheckListRetrofitModelInputList = listOf(
                HeaderCheckListRetrofitModelInput(
                    id = 1,
                    idServ = 1,
                    respItemList = listOf(
                        RespItemCheckListRetrofitModelInput(
                            id = 1,
                            idServ = 1,
                        )
                    )
                )
            )
            whenever(
                headerCheckListRoomDatasource.listBySend()
            ).thenReturn(
                Result.success(headerCheckListRoomModelList)
            )
            whenever(
                itemRespCheckListRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(itemRespCheckListRoomModelList)
            )
            whenever(
                checkListRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = headerCheckListRetrofitModelOutputList
                )
            ).thenReturn(
                Result.success(headerCheckListRetrofitModelInputList)
            )
            whenever(
                itemRespCheckListRoomDatasource.setIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerCheckListRoomDatasource.setSentAndIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
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