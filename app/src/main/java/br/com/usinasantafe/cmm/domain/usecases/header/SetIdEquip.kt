package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface SetIdEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetIdEquip @Inject constructor(
    private val configRepository: ConfigRepository,
    private val headerMotoMecRepository: HeaderMotoMecRepository
): SetIdEquip {

    override suspend fun invoke(): Result<Boolean> {
        val resultGetConfig = configRepository.get()
        if (resultGetConfig.isFailure) {
            val e = resultGetConfig.exceptionOrNull()!!
            return resultFailure(
                context = "IGetDescrEquip",
                message = e.message,
                cause = e.cause
            )
        }
        val config = resultGetConfig.getOrNull()!!
        val result = headerMotoMecRepository.setIdEquip(config.idEquip!!)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

}