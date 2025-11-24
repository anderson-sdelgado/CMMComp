package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetIdEquip @Inject constructor(
    private val configRepository: ConfigRepository,
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): SetIdEquip {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetConfig = configRepository.get()
            resultGetConfig.onFailure { return Result.failure(it) }
            val config = resultGetConfig.getOrNull()!!
            val resultGetTypeEquip = equipRepository.getTypeEquipByIdEquip(config.idEquip!!)
            resultGetTypeEquip.onFailure { return Result.failure(it) }
            val typeEquip = resultGetTypeEquip.getOrNull()!!
            val resultSetIdEquip = motoMecRepository.setDataEquipHeader(
                idEquip = config.idEquip,
                typeEquip = typeEquip
            )
            resultSetIdEquip.onFailure { return Result.failure(it) }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}