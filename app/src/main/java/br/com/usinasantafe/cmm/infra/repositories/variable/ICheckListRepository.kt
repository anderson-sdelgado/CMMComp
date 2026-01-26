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
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource,
    private val itemRespCheckListSharedPreferencesDatasource: ItemRespCheckListSharedPreferencesDatasource,
    private val headerCheckListRoomDatasource: HeaderCheckListRoomDatasource,
    private val itemRespCheckListRoomDatasource: ItemRespCheckListRoomDatasource,
    private val checkListRetrofitDatasource: CheckListRetrofitDatasource
): CheckListRepository {

    override suspend fun saveHeader(entity: HeaderCheckList): EmptyResult {
        return runCatching {
            headerCheckListSharedPreferencesDatasource.clean().getOrThrow()
            val model = entity.entityToSharedPreferencesModel()
            headerCheckListSharedPreferencesDatasource.save(model).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun cleanResp(): EmptyResult {
        return runCatching {
            itemRespCheckListSharedPreferencesDatasource.clean().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun saveResp(entity: ItemRespCheckList): EmptyResult {
        return runCatching {
            itemRespCheckListSharedPreferencesDatasource.add(
                model = entity.entityToSharedPreferencesModel()
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun saveCheckList(): EmptyResult {
        return runCatching {
            val modelSharedPreferences = headerCheckListSharedPreferencesDatasource.get().getOrThrow()
            val entityHeader = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entityHeader.entityToRoomModel()
            val id = headerCheckListRoomDatasource.save(modelRoom).getOrThrow().toInt()
            val list = itemRespCheckListSharedPreferencesDatasource.list().getOrThrow()
            for(model in list) {
                val entity = model.sharedPreferencesModelToEntity()
                itemRespCheckListRoomDatasource.save(
                    itemRespCheckListRoomModel = entity.entityToRoomModel(id)
                ).getOrThrow()
            }
            headerCheckListSharedPreferencesDatasource.clean().getOrThrow()
            itemRespCheckListSharedPreferencesDatasource.clean().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun delLastRespItem(): EmptyResult {
        return runCatching {
            itemRespCheckListSharedPreferencesDatasource.delLast().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasOpen(): Result<Boolean> {
        return runCatching {
            headerCheckListSharedPreferencesDatasource.hasOpen().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasSend(): Result<Boolean> {
        return runCatching {
            headerCheckListRoomDatasource.hasSend().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun send(
        number: Long,
        token: String
    ): EmptyResult {
        return runCatching {
            val list = headerCheckListRoomDatasource.listBySend().getOrThrow()
            val modelRetrofitList =
                list.map {
                    val respItemRoomList = itemRespCheckListRoomDatasource.listByIdHeader(it.id!!).getOrThrow()
                    it.roomModelToRetrofitModel(
                        number = number,
                        respItemList = respItemRoomList.map { resp -> resp.roomModelToRetrofitModel() }
                    )
                }
            val headerCheckListRetrofitList = checkListRetrofitDatasource.send(
                token = token,
                modelList = modelRetrofitList
            ).getOrThrow()
            for(headerCheckList in headerCheckListRetrofitList){
                val respItemCheckListRetrofitList = headerCheckList.respItemList
                for(respItemCheckList in respItemCheckListRetrofitList){
                    itemRespCheckListRoomDatasource.setIdServById(
                        id = respItemCheckList.id,
                        idServ = respItemCheckList.idServ
                    ).getOrThrow()
                }
                headerCheckListRoomDatasource.setSentAndIdServById(
                    id = headerCheckList.id,
                    idServ = headerCheckList.idServ
                ).getOrThrow()
            }
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}