package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.CompoundCompostingDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CompoundCompostingRoomDatasource
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class ICompoundCompostingRoomDatasource @Inject constructor(
    private val compoundCompostingDao: CompoundCompostingDao
): CompoundCompostingRoomDatasource {
    override suspend fun hasWill(): Result<Boolean> =
        result(getClassAndMethod()) {
            val list = compoundCompostingDao.listByStatus(Status.OPEN)
            if (list.isEmpty()) return@result false
            val model = compoundCompostingDao.getByStatus(Status.OPEN)
            model.idWill != null
        }
}