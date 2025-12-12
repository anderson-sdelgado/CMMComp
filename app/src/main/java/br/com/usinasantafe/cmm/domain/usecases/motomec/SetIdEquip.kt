package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetIdEquip @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): SetIdEquip {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetIdEquip = equipRepository.getIdEquipMain()
            resultGetIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetTypeEquip = equipRepository.getTypeEquip()
            resultGetTypeEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val typeEquip = resultGetTypeEquip.getOrNull()!!
            val resultSetIdEquip = motoMecRepository.setDataEquipHeader(
                idEquip = idEquip,
                typeEquipMain = typeEquip
            )
            resultSetIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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