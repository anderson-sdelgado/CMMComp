package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMechanicRoomDatasource
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMechanicRepository @Inject constructor(
    private val noteMechanicRoomDatasource: NoteMechanicRoomDatasource
): MechanicRepository {

    override suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean> {
        val result = noteMechanicRoomDatasource.checkNoteOpenByIdHeader(idHeader)
        result.onFailure { return Result.failure(it) }
        return result
    }
}