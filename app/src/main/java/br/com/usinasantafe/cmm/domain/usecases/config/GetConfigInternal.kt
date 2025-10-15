package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.presenter.model.ConfigModel
import br.com.usinasantafe.cmm.presenter.model.toConfigModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultHasConfig.exceptionOrNull()!!
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(null)
            val resultConfig = configRepository.get()
            if (resultConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val config = resultConfig.getOrNull()!!.toConfigModel()
            return Result.success(config)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}