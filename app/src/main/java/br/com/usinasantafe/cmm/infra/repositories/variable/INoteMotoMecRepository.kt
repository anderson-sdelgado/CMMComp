package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.NoteMotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import javax.inject.Inject

class INoteMotoMecRepository @Inject constructor(
    private val noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource,
    private val noteMotoMecRoomDatasource: NoteMotoMecRoomDatasource
): NoteMotoMecRepository {

    override suspend fun setNroOS(nroOS: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "INoteMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun setIdActivity(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdActivity(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "INoteMotoMecRepository.setIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun save(idHeader: Int): Result<Boolean> {
        try {
            val resultGet = noteMotoMecSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "INoteMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val result = noteMotoMecRoomDatasource.save(
                entity.entityToRoomModel(
                    idHeader = idHeader
                )
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "INoteMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "INoteMotoMecRepository.save",
                message = "-",
                cause = e
            )
        }
    }

}