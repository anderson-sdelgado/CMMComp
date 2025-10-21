package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.RespItemCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.RespItemCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.RespItemCheckListRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRespItemCheckListRoomDatasource @Inject constructor(
    private val respItemCheckListDao: RespItemCheckListDao
): RespItemCheckListRoomDatasource {

    override suspend fun save(respItemCheckListRoomModel: RespItemCheckListRoomModel): Result<Boolean> {
        try {
            respItemCheckListDao.insert(respItemCheckListRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}