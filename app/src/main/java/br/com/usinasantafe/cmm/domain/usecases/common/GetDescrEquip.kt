package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface GetDescrEquip {
    suspend operator fun invoke(): Result<String>
}

class IGetDescrEquip @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository
): GetDescrEquip {

    override suspend fun invoke(): Result<String> {
        try {
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
            val result = equipRepository.getDescrByIdEquip(
                idEquip = config.idEquip!!
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDescrEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val description = result.getOrNull()!!
            return Result.success(description)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetDescrEquip",
                message = "-",
                cause = e
            )
        }
    }
}