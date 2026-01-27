package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RItemMenuStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IRItemMenuStopRoomDatasource @Inject constructor(
    private val rItemMenuStopDao: RItemMenuStopDao
): RItemMenuStopRoomDatasource {

    override suspend fun addAll(list: List<RItemMenuStopRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            rItemMenuStopDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            rItemMenuStopDao.deleteAll()
        }

    override suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?> =
        result(getClassAndMethod()) {
            rItemMenuStopDao.getIdStopByIdFunctionAndIdApp(idFunction, idApp)
        }

}