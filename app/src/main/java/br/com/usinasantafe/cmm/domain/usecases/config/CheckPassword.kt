package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckPassword {
    suspend operator fun invoke(password: String): Result<Boolean>
}

class ICheckPassword @Inject constructor(
    private val configRepository: ConfigRepository
): CheckPassword {

    override suspend fun invoke(password: String): Result<Boolean> =
        call(getClassAndMethod()) {
            val hasConfig = configRepository.hasConfig().getOrThrow()
            if (!hasConfig) return@call true
            val passwordConfig = configRepository.getPassword().getOrThrow()
            passwordConfig == password
        }

}