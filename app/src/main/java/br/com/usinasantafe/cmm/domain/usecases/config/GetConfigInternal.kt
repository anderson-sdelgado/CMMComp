package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.presenter.view.configuration.config.ConfigModel
import br.com.usinasantafe.cmm.presenter.view.configuration.config.toConfigModel
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
            if (resultHasConfig.isFailure) {
                val e = resultHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetConfigInternal",
                    message = e.message,
                    cause = e.cause
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(null)

            val resultConfig = configRepository.get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetConfigInternal",
                    message = e.message,
                    cause = e.cause
                )
            }

            val config = resultConfig.getOrNull()!!.toConfigModel()
            return Result.success(config)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetConfigInternal",
                message = "-",
                cause = e
            )
        }
    }

}