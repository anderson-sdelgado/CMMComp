package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMechanicRepository @Inject constructor(
    private val mechanicRoomDatasource: MechanicRoomDatasource
): MechanicRepository {

    override suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean> {
        val result = mechanicRoomDatasource.checkNoteOpenByIdHeader(idHeader)
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun setFinishNote(): Result<Boolean> {
        val result = mechanicRoomDatasource.setFinishNote()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}