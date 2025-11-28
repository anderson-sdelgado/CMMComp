package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetDescrEquip {
    suspend operator fun invoke(): Result<String>
}

class IGetDescrEquip @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): GetDescrEquip {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGetIdEquipMotoMec = motoMecRepository.getIdEquipHeader()
            resultGetIdEquipMotoMec.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquip = resultGetIdEquipMotoMec.getOrNull()!!
            val result = equipRepository.getDescrByIdEquip(
                idEquip = idEquip
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val description = result.getOrNull()!!
            return Result.success(description)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}