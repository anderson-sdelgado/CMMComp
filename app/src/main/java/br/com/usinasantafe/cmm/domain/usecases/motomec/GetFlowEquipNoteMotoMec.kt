package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowEquipNote
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetFlowEquipNoteMotoMec {
    suspend operator fun invoke(): Result<FlowEquipNote>
}

class IGetFlowEquipNoteMotoMec @Inject constructor(
    private val configRepository: ConfigRepository,
    private val motoMecRepository: MotoMecRepository,
): GetFlowEquipNoteMotoMec {

    override suspend fun invoke(): Result<FlowEquipNote> {
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