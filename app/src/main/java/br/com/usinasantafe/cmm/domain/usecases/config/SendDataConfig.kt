package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        nroEquip: String,
        app: String,
        version: String,
    ): Result<Config>
}

class ISendDataConfig @Inject constructor(
    private val configRepository: ConfigRepository
): SendDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        nroEquip: String,
        app: String,
        version: String,
    ): Result<Config> {
        try {
            val entity = Config(
                number = number.toLong(),
                password = password,
                nroEquip = nroEquip.toLong(),
                app = app.uppercase(),
                version = version
            )
            val result = configRepository.send(entity)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(result.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}