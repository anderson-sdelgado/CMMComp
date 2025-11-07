package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckTypeHeaderMotoMec {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckTypeHeaderMotoMec @Inject constructor(
    private val configRepository: ConfigRepository,
    private val motoMecRepository: MotoMecRepository,
): CheckTypeHeaderMotoMec {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetIdEquipConfig = configRepository.getIdEquip()
            if(resultGetIdEquipConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquipConfig.exceptionOrNull()!!
                )
            }
            val idEquipConfig = resultGetIdEquipConfig.getOrNull()!!
            val resultGetIdEquipMotoMec = motoMecRepository.getIdEquipHeader()
            if(resultGetIdEquipMotoMec.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquipMotoMec.exceptionOrNull()!!
                )
            }
            val idEquipMotoMec = resultGetIdEquipMotoMec.getOrNull()!!
            val ret = idEquipConfig == idEquipMotoMec
            return Result.success(ret)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}