package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RItemMenuStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRItemMenuStopRoomDatasource @Inject constructor(
    private val rItemMenuStopDao: RItemMenuStopDao
): RItemMenuStopRoomDatasource {

    override suspend fun addAll(list: List<RItemMenuStopRoomModel>): Result<Boolean> {
        try {
            rItemMenuStopDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rItemMenuStopDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?> {
        try {
            val idStop = rItemMenuStopDao.getIdStopByIdFunctionAndIdApp(
                idFunction = idFunction,
                idApp = idApp
            )
            return Result.success(idStop)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}