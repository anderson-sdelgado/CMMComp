package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.MechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IMechanicRepository @Inject constructor(
    private val mechanicRoomDatasource: MechanicRoomDatasource,
    private val mechanicSharedPreferencesDatasource: MechanicSharedPreferencesDatasource
): MechanicRepository {

    override suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            mechanicRoomDatasource.checkNoteOpenByIdHeader(idHeader).getOrThrow()
        }

    override suspend fun setFinishNote(): EmptyResult =
        call(getClassAndMethod()) {
            mechanicRoomDatasource.setFinishNote().getOrThrow()
        }

    override suspend fun setNroOS(nroOS: Int): EmptyResult =
        call(getClassAndMethod()) {
            mechanicSharedPreferencesDatasource.setNroOS(nroOS).getOrThrow()
        }

    override suspend fun setSeqItem(seqItem: Int): EmptyResult =
        call(getClassAndMethod()) {
            mechanicSharedPreferencesDatasource.setSeqItem(seqItem).getOrThrow()
        }

    override suspend fun getNroOS(): Result<Int> =
        call(getClassAndMethod()) {
            mechanicSharedPreferencesDatasource.getNroOS().getOrThrow()
        }

    override suspend fun save(idHeader: Int): EmptyResult =
        call(getClassAndMethod()) {
            val modelSharedPreferences = mechanicSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = runCatching {
                entity.entityToRoomModel(idHeader)
            }.getOrElse { e ->
                throw Exception(entity::entityToRoomModel.name, e)
            }
            mechanicRoomDatasource.save(modelRoom).getOrThrow()
        }

}