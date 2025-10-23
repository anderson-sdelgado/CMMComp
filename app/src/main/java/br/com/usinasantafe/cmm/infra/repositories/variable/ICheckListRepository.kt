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
            if(resultClean.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultClean.exceptionOrNull()!!
                )
            }
            val model = entity.entityToSharedPreferencesModel()
            val resultSave = headerCheckListSharedPreferencesDatasource.save(model)
            if(resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
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
        if(result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun saveResp(entity: RespItemCheckList): Result<Boolean> {
        try {
            val result = respItemCheckListSharedPreferencesDatasource.save(
                model = entity.entityToSharedPreferencesModel()
            )
            if(result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
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
            if(resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entityHeader = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entityHeader.entityToRoomModel()
            val resultSaveHeader = headerCheckListRoomDatasource.save(modelRoom)
            if(resultSaveHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSaveHeader.exceptionOrNull()!!
                )
            }
            val id = resultSaveHeader.getOrNull()!!.toInt()
            val resultListResp = respItemCheckListSharedPreferencesDatasource.list()
            if(resultListResp.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListResp.exceptionOrNull()!!
                )
            }
            val list = resultListResp.getOrNull()!!
            for(model in list) {
                val entity = model.sharedPreferencesModelToEntity()
                val resultSave = respItemCheckListRoomDatasource.save(
                    respItemCheckListRoomModel = entity.entityToRoomModel(id)
                )
                if(resultSave.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultSave.exceptionOrNull()!!
                    )
                }
            }
            val resultHeaderClean = headerCheckListSharedPreferencesDatasource.clean()
            if(resultHeaderClean.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultHeaderClean.exceptionOrNull()!!
                )
            }
            val resultRespItemClean = respItemCheckListSharedPreferencesDatasource.clean()
            if(resultRespItemClean.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultRespItemClean.exceptionOrNull()!!
                )
            }
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
        if(result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun checkOpen(): Result<Boolean> {
        val result = headerCheckListSharedPreferencesDatasource.checkOpen()
        if(result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = headerCheckListRoomDatasource.checkSend()
        if(result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean> {
        try {
            val resultListSend = headerCheckListRoomDatasource.listBySend() //ok
            if(resultListSend.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListSend.exceptionOrNull()!!
                )
            }
            val modelRetrofitList =
                resultListSend.getOrNull()!!.map {
                    val resultRespItemList = respItemCheckListRoomDatasource.listByIdHeader(it.id!!)
                    if(resultRespItemList.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultRespItemList.exceptionOrNull()!!
                        )
                    }
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
            if(resultSend.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSend.exceptionOrNull()!!
                )
            }
            val headerCheckListRetrofitList = resultSend.getOrNull()!!
            for(headerCheckList in headerCheckListRetrofitList){
                val respItemCheckListRetrofitList = headerCheckList.respItemList
                for(respItemCheckList in respItemCheckListRetrofitList){
                    val result = respItemCheckListRoomDatasource.setIdServById(
                        id = respItemCheckList.id,
                        idServ = respItemCheckList.idServ
                    )
                    if(result.isFailure){
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = result.exceptionOrNull()!!
                        )
                    }
                }
                val result = headerCheckListRoomDatasource.setSentAndIdServById(
                    id = headerCheckList.id,
                    idServ = headerCheckList.idServ
                )
                if(result.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = result.exceptionOrNull()!!
                    )
                }
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