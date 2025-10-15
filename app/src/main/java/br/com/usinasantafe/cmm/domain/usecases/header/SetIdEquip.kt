package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.TypeEquip
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
            if (resultGetConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetConfig.exceptionOrNull()!!
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultGetTypeEquip = equipRepository.getTypeFertByIdEquip(config.idEquip!!)
            if (resultGetTypeEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetTypeEquip.exceptionOrNull()!!
                )
            }
            val typeEquipBD = resultGetTypeEquip.getOrNull()!!
            val typeEquip = if(typeEquipBD <= 2) TypeEquip.NORMAL else TypeEquip.FERT
            val resultSetIdEquip = motoMecRepository.setDataEquipHeader(
                idEquip = config.idEquip,
                typeEquip = typeEquip
            )
            if (resultSetIdEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSetIdEquip.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}