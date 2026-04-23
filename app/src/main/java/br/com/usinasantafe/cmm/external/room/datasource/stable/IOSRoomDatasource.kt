package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IOSRoomDatasource @Inject constructor(
    private val osDao: OSDao
) : OSRoomDatasource {

    override suspend fun addAll(list: List<OSRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            osDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            osDao.deleteAll()
        }

    override suspend fun hasByNro(nro: Int): Result<Boolean> =
        result(getClassAndMethod()) {
            osDao.hasNro(nro)
        }

    override suspend fun add(model: OSRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            osDao.insert(model)
        }

    override suspend fun getByNro(nro: Int): Result<OSRoomModel> =
        result(getClassAndMethod()) {
            osDao.getByNro(nro)
        }

    override suspend fun hasByNroAndIdRelease(
        nroOS: Int,
        idRelease: Int
    ): Result<Boolean> =
        result(getClassAndMethod()) {
            osDao.hasNroAndIdRelease(nroOS, idRelease)
        }

}
