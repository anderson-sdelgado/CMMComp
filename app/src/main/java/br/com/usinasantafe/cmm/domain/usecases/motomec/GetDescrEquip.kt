package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
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
        return runCatching {
            val idEquip = motoMecRepository.getIdEquipHeader().getOrThrow()
            equipRepository.getDescrByIdEquip(idEquip = idEquip).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }
}