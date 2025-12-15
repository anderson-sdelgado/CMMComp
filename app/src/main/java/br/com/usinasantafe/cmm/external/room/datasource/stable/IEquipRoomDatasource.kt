package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRoomDatasource @Inject constructor(
    private val equipDao: EquipDao
): EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean> {
        try {
            equipDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            equipDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getDescrByIdEquip(id: Int): Result<String> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success("${model.nro} - ${model.descrClass}")
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun hasByNroEquipAndTypeEquip(
        nroEquip: Long,
        typeEquip: TypeEquipSecondary
    ): Result<Boolean> {
        try {
            val count = equipDao.countByNroEquipAndTypeEquip(nroEquip, typeEquip)
            return Result.success(count > 0)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}