package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdEquipMotoMec {
    suspend operator fun invoke(): EmptyResult
}

class ISetIdEquipMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): SetIdEquipMotoMec {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            val typeEquip = equipRepository.getTypeEquipMain().getOrThrow()
            motoMecRepository.setDataEquipHeader(idEquip = idEquip, typeEquip = typeEquip).getOrThrow()
        }

}