package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IEquipRoomDatasource @Inject constructor(
    private val equipDao: EquipDao
): EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            equipDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            equipDao.deleteAll()
        }

    override suspend fun getDescrById(id: Int): Result<String> =
        result(getClassAndMethod()) {
            val model = equipDao.getByIdEquip(id)
            "${model.nro} - ${model.descrClass}"
        }

    override suspend fun hasByNroAndType(
        nroEquip: Long,
        typeEquip: TypeEquip
    ): Result<Boolean> =
        result(getClassAndMethod()) {
            equipDao.countByNroEquipAndTypeEquip(nroEquip, typeEquip) > 0
        }

    override suspend fun getIdByNro(nro: Long): Result<Int> =
        result(getClassAndMethod()) {
            equipDao.getByNroEquip(nro).id
        }

}