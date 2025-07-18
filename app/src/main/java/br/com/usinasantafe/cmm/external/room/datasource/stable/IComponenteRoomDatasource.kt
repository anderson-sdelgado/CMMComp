package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.room.dao.stable.ComponenteDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ComponenteRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponenteRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IComponenteRoomDatasource @Inject constructor(
    private val componenteDao: ComponenteDao
) : ComponenteRoomDatasource {
    override suspend fun addAll(list: List<ComponenteRoomModel>): Result<Boolean> {
        try {
            componenteDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            componenteDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
