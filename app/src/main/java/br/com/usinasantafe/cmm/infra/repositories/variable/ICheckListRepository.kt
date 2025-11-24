package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.CheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.RespItemCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.RespItemCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource,
    private val respItemCheckListSharedPreferencesDatasource: RespItemCheckListSharedPreferencesDatasource,
    private val headerCheckListRoomDatasource: HeaderCheckListRoomDatasource,
    private val respItemCheckListRoomDatasource: RespItemCheckListRoomDatasource,
    private val checkListRetrofitDatasource: CheckListRetrofitDatasource
): CheckListRepository {

    override suspend fun saveHeader(entity: HeaderCheckList): Result<Boolean> {
        try {
            val resultClean = headerCheckListSharedPreferencesDatasource.clean()
            resultClean.onFailure { return Result.failure(it) }
            val model = entity.entityToSharedPreferencesModel()
            val resultSave = headerCheckListSharedPreferencesDatasource.save(model)
            resultSave.onFailure { return Result.failure(it) }
            return resultSave
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun cleanResp(): Result<Boolean> {
        val result = respItemCheckListSharedPreferencesDatasource.clean()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun saveResp(entity: RespItemCheckList): Result<Boolean> {
        try {
            val result = respItemCheckListSharedPreferencesDatasource.save(
                model = entity.entityToSharedPreferencesModel()
            )
            result.onFailure { return Result.failure(it) }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun saveCheckList(): Result<Boolean> {
        try {
            val resultGet = headerCheckListSharedPreferencesDatasource.get()
            resultGet.onFailure { return Result.failure(it) }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entityHeader = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entityHeader.entityToRoomModel()
            val resultSaveHeader = headerCheckListRoomDatasource.save(modelRoom)
            resultSaveHeader.onFailure { return Result.failure(it) }
            val id = resultSaveHeader.getOrNull()!!.toInt()
            val resultListResp = respItemCheckListSharedPreferencesDatasource.list()
            resultListResp.onFailure { return Result.failure(it) }
            val list = resultListResp.getOrNull()!!
            for(model in list) {
                val entity = model.sharedPreferencesModelToEntity()
                val resultSave = respItemCheckListRoomDatasource.save(
                    respItemCheckListRoomModel = entity.entityToRoomModel(id)
                )
                resultSave.onFailure { return Result.failure(it) }
            }
            val resultHeaderClean = headerCheckListSharedPreferencesDatasource.clean()
            resultHeaderClean.onFailure { return Result.failure(it) }
            val resultRespItemClean = respItemCheckListSharedPreferencesDatasource.clean()
            resultRespItemClean.onFailure { return Result.failure(it) }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun delLastRespItem(): Result<Boolean> {
        val result = respItemCheckListSharedPreferencesDatasource.delLast()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun checkOpen(): Result<Boolean> {
        val result = headerCheckListSharedPreferencesDatasource.checkOpen()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = headerCheckListRoomDatasource.checkSend()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean> {
        try {
            val resultListSend = headerCheckListRoomDatasource.listBySend() //ok
            resultListSend.onFailure { return Result.failure(it) }
            val modelRetrofitList =
                resultListSend.getOrNull()!!.map {
                    val resultRespItemList = respItemCheckListRoomDatasource.listByIdHeader(it.id!!)
                    resultRespItemList.onFailure { return Result.failure(it) }
                    val respItemRoomList = resultRespItemList.getOrNull()!!
                    it.roomModelToRetrofitModel(
                        number = number,
                        respItemList = respItemRoomList.map { resp -> resp.roomModelToRetrofitModel() }
                    )
                }
            val resultSend = checkListRetrofitDatasource.send(
                token = token,
                modelList = modelRetrofitList
            )
            resultSend.onFailure { return Result.failure(it) }
            val headerCheckListRetrofitList = resultSend.getOrNull()!!
            for(headerCheckList in headerCheckListRetrofitList){
                val respItemCheckListRetrofitList = headerCheckList.respItemList
                for(respItemCheckList in respItemCheckListRetrofitList){
                    val result = respItemCheckListRoomDatasource.setIdServById(
                        id = respItemCheckList.id,
                        idServ = respItemCheckList.idServ
                    )
                    result.onFailure { return Result.failure(it) }
                }
                val result = headerCheckListRoomDatasource.setSentAndIdServById(
                    id = headerCheckList.id,
                    idServ = headerCheckList.idServ
                )
                result.onFailure { return Result.failure(it) }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}