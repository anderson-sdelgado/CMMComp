package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
        idEquip: Int,
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
        idEquip: Int
    ): Result<Boolean> {
        try {
            val entity = Config(
                number = number.toLong(),
                password = password,
                version = version,
                app = app.uppercase(),
                nroEquip = nroEquip.toLong(),
                checkMotoMec = checkMotoMec,
                idBD = idBD,
                idEquip = idEquip
            )
            val result = configRepository.save(entity)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}