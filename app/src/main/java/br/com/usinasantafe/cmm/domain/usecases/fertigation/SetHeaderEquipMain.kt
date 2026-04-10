package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetHeaderEquipMain {
    suspend operator fun invoke(): EmptyResult
}

class ISetHeaderEquipMain @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): SetHeaderEquipMain {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            motoMecRepository.openHeaderByIdEquip(idEquip).getOrThrow()
        }

}