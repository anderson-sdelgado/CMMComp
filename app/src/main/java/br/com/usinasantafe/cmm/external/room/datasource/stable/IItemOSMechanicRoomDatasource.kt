package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ItemOSMechanicDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMechanicRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMechanicRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IItemOSMechanicRoomDatasource @Inject constructor(
    private val itemOSMechanicDao: ItemOSMechanicDao
): ItemOSMechanicRoomDatasource {

    override suspend fun addAll(list: List<ItemOSMechanicRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            itemOSMechanicDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            itemOSMechanicDao.deleteAll()
        }

}
