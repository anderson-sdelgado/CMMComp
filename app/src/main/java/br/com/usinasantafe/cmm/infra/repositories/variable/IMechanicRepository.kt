package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMechanicRepository @Inject constructor(
    private val mechanicRoomDatasource: MechanicRoomDatasource
): MechanicRepository {

    override suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean> {
        return runCatching {
            mechanicRoomDatasource.checkNoteOpenByIdHeader(idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setFinishNote(): EmptyResult {
        return runCatching {
            mechanicRoomDatasource.setFinishNote().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}