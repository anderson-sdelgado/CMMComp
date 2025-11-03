package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMechanicRoomDatasource
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class INoteMechanicRoomDatasource @Inject constructor(
    private val noteMechanicDao: NoteMechanicDao
): NoteMechanicRoomDatasource {

    override suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean> {
        try {
            val count = noteMechanicDao.countOpenByIdHeader(idHeader)
            return Result.success(count > 0)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}