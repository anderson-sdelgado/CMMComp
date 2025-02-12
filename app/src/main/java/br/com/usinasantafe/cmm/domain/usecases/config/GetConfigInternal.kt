package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.presenter.configuration.config.ConfigModel
import br.com.usinasantafe.cmm.presenter.configuration.config.toConfigModel
import javax.inject.Inject

interface GetConfigInternal {
    suspend operator fun invoke(): Result<ConfigModel?>
}

class IGetConfigInternal @Inject constructor(
    private val configRepository: ConfigRepository
): GetConfigInternal {

    override suspend fun invoke(): Result<ConfigModel?> {
        try {
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure)
                return Result.failure(resultHasConfig.exceptionOrNull()!!)

            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(null)

            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)

            val config = resultConfig.getOrNull()!!.toConfigModel()
            return Result.success(config)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetConfigInternal",
                    cause = e
                )
            )
        }
    }

}