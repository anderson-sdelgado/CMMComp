package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroEquipPreCEC {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetNroEquipPreCEC @Inject constructor(
    private val equipRepository: EquipRepository,
    private val cecRepository: CECRepository
): SetNroEquipPreCEC {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val nroEquip = equipRepository.getNroEquipMain().getOrThrow()
            cecRepository.setNroEquip(nroEquip).getOrThrow()
            equipRepository.getCodClass().getOrThrow() == 1
        }

}