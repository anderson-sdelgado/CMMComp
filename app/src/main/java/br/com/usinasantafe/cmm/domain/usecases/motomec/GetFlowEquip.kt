package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowEquipNote
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetFlowEquip {
    suspend operator fun invoke(): Result<FlowEquipNote>
}

class IGetFlowEquip @Inject constructor(
    private val configRepository: ConfigRepository,
    private val motoMecRepository: MotoMecRepository,
): GetFlowEquip {

    override suspend fun invoke(): Result<FlowEquipNote> {
        try {
            val resultGetIdEquipConfig = configRepository.getIdEquip()
            resultGetIdEquipConfig.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquipConfig = resultGetIdEquipConfig.getOrNull()!!
            val resultGetIdEquipMotoMec = motoMecRepository.getIdEquipHeader()
            resultGetIdEquipMotoMec.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquipMotoMec = resultGetIdEquipMotoMec.getOrNull()!!
            val ret = if(idEquipConfig == idEquipMotoMec) FlowEquipNote.MAIN else FlowEquipNote.SECONDARY
            return Result.success(ret)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}