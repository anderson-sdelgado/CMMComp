package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.retrofitToEntity
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.FlagUpdate
import javax.inject.Inject

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
): ConfigRepository {

    override suspend fun get(): Result<Config> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.get",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = resultConfig.getOrNull()!!
            return Result.success(config.sharedPreferencesModelToEntity())
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        val result = configSharedPreferencesDatasource.getFlagUpdate()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.getFlagUpdate",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getPassword(): Result<String> {
        val result = configSharedPreferencesDatasource.getPassword()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.getPassword",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun hasConfig(): Result<Boolean> {
        return try {
            configSharedPreferencesDatasource.has()
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigRepository.hasConfig",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun send(config: Config): Result<Config> {
        try {
            val resultRecoverToken = configRetrofitDatasource.recoverToken(
                config.entityToRetrofitModel()
            )
            if(resultRecoverToken.isFailure) {
                val e = resultRecoverToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.send",
                    message = e.message,
                    cause = e.cause
                )
            }
            val configRetrofitModel = resultRecoverToken.getOrNull()!!
            val entity = configRetrofitModel.retrofitToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(config: Config): Result<Boolean> {
        try {
            val sharedPreferencesModel = config.entityToSharedPreferencesModel()
            return configSharedPreferencesDatasource.save(sharedPreferencesModel)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        val result = configSharedPreferencesDatasource.setFlagUpdate(flagUpdate)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.setFlagUpdate",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }
}