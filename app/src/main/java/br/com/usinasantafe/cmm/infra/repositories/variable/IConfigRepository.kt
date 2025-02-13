package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.RepositoryException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.entityToRetrofitModel
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
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            return Result.success(config.sharedPreferencesModelToEntity())
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepository.getConfig",
                    cause = e
                )
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            return Result.success(config.flagUpdate)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepository.getFlagUpdate",
                    cause = e
                )
            )
        }
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            return Result.success(config.sharedPreferencesModelToEntity().password!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepository.getConfig",
                    cause = e
                )
            )
        }
    }

    override suspend fun hasConfig(): Result<Boolean> {
        return configSharedPreferencesDatasource.has()
    }

    override suspend fun send(config: Config): Result<Int> {
        try {
            val resultRecoverToken = configRetrofitDatasource.recoverToken(
                config.entityToRetrofitModel()
            )
            if(resultRecoverToken.isFailure)
                return Result.failure(resultRecoverToken.exceptionOrNull()!!)
            val configRetrofitModel = resultRecoverToken.getOrNull()!!
            return Result.success(configRetrofitModel.idBD)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepository.recoverToken",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(config: Config): Result<Boolean> {
        try {
            val sharedPreferencesModel = config.entityToSharedPreferencesModel()
            return configSharedPreferencesDatasource.save(sharedPreferencesModel)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepository.save",
                    cause = e
                )
            )
        }
    }
}