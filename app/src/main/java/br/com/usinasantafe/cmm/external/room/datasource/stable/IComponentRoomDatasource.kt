package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ComponentRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IComponentRoomDatasource @Inject constructor(
    private val componentDao: ComponentDao
): ComponentRoomDatasource {

    override suspend fun addAll(list: List<ComponentRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            componentDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            componentDao.deleteAll()
        }
}