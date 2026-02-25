package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ServiceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IServiceRoomDatasource @Inject constructor(
    private val serviceDao: ServiceDao
): ServiceRoomDatasource {
    override suspend fun addAll(list: List<ServiceRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            serviceDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            serviceDao.deleteAll()
        }
}