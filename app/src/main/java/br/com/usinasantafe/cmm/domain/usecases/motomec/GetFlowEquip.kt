package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowEquipNote
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetFlowEquip {
    suspend operator fun invoke(): Result<FlowEquipNote>
}

class IGetFlowEquip @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository,
): GetFlowEquip {

    override suspend fun invoke(): Result<FlowEquipNote> {
        return runCatching {
            val idEquipConfig = equipRepository.getIdEquipMain().getOrThrow()
            val idEquipMotoMec = motoMecRepository.getIdEquipHeader().getOrThrow()
            if(idEquipConfig == idEquipMotoMec) FlowEquipNote.MAIN else FlowEquipNote.SECONDARY
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}