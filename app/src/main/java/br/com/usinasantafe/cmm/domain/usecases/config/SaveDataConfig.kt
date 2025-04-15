package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
            val configResult = configRepository.save(config)
            if (configResult.isFailure) {
                val e = configResult.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveDataConfig",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISaveDataConfig",
                message = "-",
                cause = e
            )
        }
    }

}