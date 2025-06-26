package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.TypeEquip
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
            if (resultGetConfig.isFailure) {
                val e = resultGetConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultGetTypeEquip = equipRepository.getTypeFertByIdEquip(config.idEquip!!)
            if (resultGetTypeEquip.isFailure) {
                val e = resultGetTypeEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val typeEquipBD = resultGetTypeEquip.getOrNull()!!
            val typeEquip = if(typeEquipBD <= 2) TypeEquip.NORMAL else TypeEquip.FERT
            val resultSetIdEquip = motoMecRepository.setDataEquipHeader(
                idEquip = config.idEquip,
                typeEquip = typeEquip
            )
            if (resultSetIdEquip.isFailure) {
                val e = resultSetIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetIdEquip",
                message = "-",
                cause = e
            )
        }
    }

}