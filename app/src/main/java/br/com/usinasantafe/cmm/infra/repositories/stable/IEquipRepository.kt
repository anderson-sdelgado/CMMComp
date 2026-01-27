package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityEquipToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IEquipRepository @Inject constructor(
    private val equipRetrofitDatasource: EquipRetrofitDatasource,
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource
): EquipRepository {

    override suspend fun saveEquipMain(entity: Equip): EmptyResult =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            equipSharedPreferencesDatasource.save(sharedPreferencesModel).getOrThrow()
        }

    override suspend fun addAll(list: List<Equip>): EmptyResult  =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityEquipToRoomModel() }
            equipRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult  =
        call(getClassAndMethod()) {
            equipRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<Equip>>  =
        call(getClassAndMethod()) {
            val modelList = equipRetrofitDatasource.listAll(token).getOrThrow()
             modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun getIdEquipMain(): Result<Int> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getId().getOrThrow()
        }

    override suspend fun getNroEquipMain(): Result<Long> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getNro().getOrThrow()
        }


    override suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String> =
        call(getClassAndMethod()) {
            val idEquipSharedPreferences = equipSharedPreferencesDatasource.getId().getOrThrow()
            if(idEquip == idEquipSharedPreferences) {
                val descr = equipSharedPreferencesDatasource.getDescr().getOrThrow()
                return@call descr
            }
            equipRoomDatasource.getDescrById(idEquip).getOrThrow()
        }

    override suspend fun getCodTurnEquip(): Result<Int> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getCodTurnEquip().getOrThrow()
        }

    override suspend fun getHourMeter(): Result<Double> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getHourMeter().getOrThrow()
        }

    override suspend fun updateHourMeter(
        hourMeter: Double
    ): EmptyResult =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.updateHourMeter(hourMeter).getOrThrow()
        }

    override suspend fun getTypeEquipMain(): Result<TypeEquip> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getTypeEquip().getOrThrow()
        }

    override suspend fun getIdCheckList(): Result<Int> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getIdCheckList().getOrThrow()
        }

    override suspend fun getFlagMechanic(): Result<Boolean> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getFlagMechanic().getOrThrow()
        }

    override suspend fun getFlagTire(): Result<Boolean> =
        call(getClassAndMethod()) {
            equipSharedPreferencesDatasource.getFlagTire().getOrThrow()
        }

    override suspend fun hasEquipSecondary(
        nroEquip: Long,
        typeEquip: TypeEquip
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            equipRoomDatasource.hasByNroAndType(nroEquip, typeEquip).getOrThrow()
        }

    override suspend fun getIdByNro(nroEquip: Long): Result<Int> =
        call(getClassAndMethod()) {
            equipRoomDatasource.getIdByNro(nroEquip).getOrThrow()
        }


}