package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.ItemRespCheckList
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.CheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemRespCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemRespCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource,
    private val itemRespCheckListSharedPreferencesDatasource: ItemRespCheckListSharedPreferencesDatasource,
    private val headerCheckListRoomDatasource: HeaderCheckListRoomDatasource,
    private val itemRespCheckListRoomDatasource: ItemRespCheckListRoomDatasource,
    private val checkListRetrofitDatasource: CheckListRetrofitDatasource
): CheckListRepository {

    override suspend fun saveHeader(entity: HeaderCheckList): Result<Boolean> {
        try {
            val resultClean = headerCheckListSharedPreferencesDatasource.clean()
            resultClean.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = entity.entityToSharedPreferencesModel()
            val resultSave = headerCheckListSharedPreferencesDatasource.save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
        val result = itemRespCheckListSharedPreferencesDatasource.clean()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun saveResp(entity: ItemRespCheckList): Result<Boolean> {
        try {
            val result = itemRespCheckListSharedPreferencesDatasource.add(
                model = entity.entityToSharedPreferencesModel()
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entityHeader = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entityHeader.entityToRoomModel()
            val resultSaveHeader = headerCheckListRoomDatasource.save(modelRoom)
            resultSaveHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val id = resultSaveHeader.getOrNull()!!.toInt()
            val resultListResp = itemRespCheckListSharedPreferencesDatasource.list()
            resultListResp.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val list = resultListResp.getOrNull()!!
            for(model in list) {
                val entity = model.sharedPreferencesModelToEntity()
                val resultSave = itemRespCheckListRoomDatasource.save(
                    itemRespCheckListRoomModel = entity.entityToRoomModel(id)
                )
                resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            }
            val resultHeaderClean = headerCheckListSharedPreferencesDatasource.clean()
            resultHeaderClean.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultRespItemClean = itemRespCheckListSharedPreferencesDatasource.clean()
            resultRespItemClean.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
        val result = itemRespCheckListSharedPreferencesDatasource.delLast()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun checkOpen(): Result<Boolean> {
        val result = headerCheckListSharedPreferencesDatasource.checkOpen()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun hasSend(): Result<Boolean> {
        val result = headerCheckListRoomDatasource.hasSend()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
            resultListSend.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val modelRetrofitList =
                resultListSend.getOrNull()!!.map {
                    val resultRespItemList = itemRespCheckListRoomDatasource.listByIdHeader(it.id!!)
                    resultRespItemList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
            resultSend.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerCheckListRetrofitList = resultSend.getOrNull()!!
            for(headerCheckList in headerCheckListRetrofitList){
                val respItemCheckListRetrofitList = headerCheckList.respItemList
                for(respItemCheckList in respItemCheckListRetrofitList){
                    val result = itemRespCheckListRoomDatasource.setIdServById(
                        id = respItemCheckList.id,
                        idServ = respItemCheckList.idServ
                    )
                    result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                }
                val result = headerCheckListRoomDatasource.setSentAndIdServById(
                    id = headerCheckList.id,
                    idServ = headerCheckList.idServ
                )
                result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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