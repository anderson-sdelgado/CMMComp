package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface SaveDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String,
        app: String,
        nroEquip: String,
        checkMotoMec: Boolean,
        idBD: Int,
    ): Result<Boolean>
}

class ISaveDataConfig @Inject constructor(
    private val configRepository: ConfigRepository
): SaveDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String,
        app: String,
        nroEquip: String,
        checkMotoMec: Boolean,
        idBD: Int,
    ): Result<Boolean> {
        try {
            val config = Config(
                number = number.toLong(),
                password = password,
                version = version,
                app = app.uppercase(),
                nroEquip = nroEquip.toLong(),
                checkMotoMec = checkMotoMec,
                idBD = idBD
            )
            return configRepository.save(config)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SaveDataConfig",
                    cause = e
                )
            )
        }
    }

}