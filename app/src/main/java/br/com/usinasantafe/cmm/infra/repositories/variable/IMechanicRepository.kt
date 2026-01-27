package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IMechanicRepository @Inject constructor(
    private val mechanicRoomDatasource: MechanicRoomDatasource
): MechanicRepository {

    override suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            mechanicRoomDatasource.checkNoteOpenByIdHeader(idHeader).getOrThrow()
        }

    override suspend fun setFinishNote(): EmptyResult =
        call(getClassAndMethod()) {
            mechanicRoomDatasource.setFinishNote().getOrThrow()
        }

}