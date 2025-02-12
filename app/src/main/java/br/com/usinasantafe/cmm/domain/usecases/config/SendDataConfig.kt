package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface SendDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        nroEquip: String,
        app: String,
        version: String,
    ): Result<Int>
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
    ): Result<Int> {
        try {
            val config = Config(
                number = number.toLong(),
                password = password,
                nroEquip = nroEquip.toLong(),
                app = app.uppercase(),
                version = version
            )
            return configRepository.send(config)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SendDataConfig",
                    cause = e
                )
            )
        }
    }

}