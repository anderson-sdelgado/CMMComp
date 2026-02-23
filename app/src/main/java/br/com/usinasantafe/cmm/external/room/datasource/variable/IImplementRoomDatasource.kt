package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.ImplementDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ImplementRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.ImplementRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IImplementRoomDatasource @Inject constructor(
    private val implementDao: ImplementDao
): ImplementRoomDatasource {

    override suspend fun save(implementRoomModel: ImplementRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            implementDao.insert(implementRoomModel)
        }

}