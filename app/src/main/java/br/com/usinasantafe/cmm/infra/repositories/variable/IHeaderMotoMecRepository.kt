package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import javax.inject.Inject

class IHeaderMotoMecRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource,
    private val headerMotoMecRoomDatasource: HeaderMotoMecRoomDatasource
): HeaderMotoMecRepository {

    override suspend fun setRegOperator(regOperator: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setRegOperator",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setIdEquip(idEquip: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdEquip(idEquip)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setIdTurn(idTurn: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setIdTurn",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setNroOS(nroOS: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun getNroOS(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getNroOS()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.getNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(result.getOrNull()!!)
    }

    override suspend fun setIdActivity(idActivity: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun getIdEquip(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdEquip()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.getIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(result.getOrNull()!!)
    }

    override suspend fun setMeasureInitial(measure: Double): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setMeasureInitial(measure)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setMeasureInitial",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun save(): Result<Boolean> {
        try {
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entity.entityToRoomModel()
            val resultAdd = headerMotoMecRoomDatasource.save(modelRoom)
            if(resultAdd.isFailure){
                val e = resultAdd.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecRepository.save",
                message = "-",
                cause = e
            )
        }
    }

}