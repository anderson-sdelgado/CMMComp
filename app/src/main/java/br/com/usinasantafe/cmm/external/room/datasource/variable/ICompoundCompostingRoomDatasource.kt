package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.CompoundCompostingDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CompoundCompostingRoomDatasource
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICompoundCompostingRoomDatasource @Inject constructor(
    private val compoundCompostingDao: CompoundCompostingDao
): CompoundCompostingRoomDatasource {
    override suspend fun hasWill(): Result<Boolean> {
        try {
            val list = compoundCompostingDao.listByStatus(Status.OPEN)
            if (list.isEmpty()) return Result.success(false)
            val model = compoundCompostingDao.getByStatus(Status.OPEN)
            val check = model.idWill != null
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}