package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.FrenteDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FrenteRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FrenteRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFrenteRoomDatasource @Inject constructor(
    private val frenteDao: FrenteDao
) : FrenteRoomDatasource {
    override suspend fun addAll(list: List<FrenteRoomModel>): Result<Boolean> {
        try {
            frenteDao.insertAll(list)
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
            frenteDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
