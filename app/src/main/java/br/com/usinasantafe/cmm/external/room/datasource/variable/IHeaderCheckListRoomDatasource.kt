package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IHeaderCheckListRoomDatasource @Inject constructor(
    private val headerCheckListDao: HeaderCheckListDao
): HeaderCheckListRoomDatasource {

    override suspend fun save(headerCheckListRoomModel: HeaderCheckListRoomModel): Result<Long> {
        try {
            val id = headerCheckListDao.insert(headerCheckListRoomModel)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}