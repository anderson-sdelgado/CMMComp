package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityEquipToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRepository @Inject constructor(
    private val equipRetrofitDatasource: EquipRetrofitDatasource,
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource
): EquipRepository {

    override suspend fun saveEquipMain(entity: Equip): EmptyResult {
        return runCatching {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            equipSharedPreferencesDatasource.save(sharedPreferencesModel).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun addAll(list: List<Equip>): EmptyResult {
        return runCatching {
            val roomModelList = list.map { it.entityEquipToRoomModel() }
            equipRoomDatasource.addAll(roomModelList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun deleteAll(): EmptyResult {
        return runCatching {
            equipRoomDatasource.deleteAll().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun listAll(
        token: String
    ): Result<List<Equip>> {
        return runCatching {
            val modelList = equipRetrofitDatasource.listAll(token).getOrThrow()
             modelList.map { it.retrofitModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdEquipMain(): Result<Int> {
        return runCatching {
            equipSharedPreferencesDatasource.getId().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getNroEquipMain(): Result<Long> {
        return runCatching {
            equipSharedPreferencesDatasource.getNro().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }


    override suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String> {
        return runCatching {
            val idEquipSharedPreferences = equipSharedPreferencesDatasource.getId().getOrThrow()
            if(idEquip == idEquipSharedPreferences) {
                val descr = equipSharedPreferencesDatasource.getDescr().getOrThrow()
                return Result.success(descr)
            }
            equipRoomDatasource.getDescrById(idEquip).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getCodTurnEquip(): Result<Int> {
        return runCatching {
            equipSharedPreferencesDatasource.getCodTurnEquip().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getHourMeter(): Result<Double> {
        return runCatching {
            equipSharedPreferencesDatasource.getHourMeter().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun updateHourMeter(
        hourMeter: Double
    ): EmptyResult {
        return runCatching {
            equipSharedPreferencesDatasource.updateHourMeter(hourMeter).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getTypeEquipMain(): Result<TypeEquip> {
        return runCatching {
            equipSharedPreferencesDatasource.getTypeEquip().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdCheckList(): Result<Int> {
        return runCatching {
            equipSharedPreferencesDatasource.getIdCheckList().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getFlagMechanic(): Result<Boolean> {
        return runCatching {
            equipSharedPreferencesDatasource.getFlagMechanic().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getFlagTire(): Result<Boolean> {
        return runCatching {
            equipSharedPreferencesDatasource.getFlagTire().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasEquipSecondary(
        nroEquip: Long,
        typeEquip: TypeEquip
    ): Result<Boolean> {
        return runCatching {
            equipRoomDatasource.hasByNroAndType(
                nroEquip = nroEquip,
                typeEquip = typeEquip
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdByNro(nroEquip: Long): Result<Int> {
        return runCatching {
            equipRoomDatasource.getIdByNro(nroEquip).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }


}