package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import javax.inject.Inject

class INoteMotoMecRoomDatasource @Inject constructor(
    private val noteMotoMecDao: NoteMotoMecDao
): NoteMotoMecRoomDatasource {

    override suspend fun save(noteMotoMecRoomModel: NoteMotoMecRoomModel): Result<Boolean> {
        try {
            noteMotoMecDao.insert(noteMotoMecRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "INoteMotoMecRoomDatasource.insert",
                message = "-",
                cause = e
            )
        }
    }

}