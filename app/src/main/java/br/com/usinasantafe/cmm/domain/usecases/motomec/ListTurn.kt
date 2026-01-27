package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListTurn {
    suspend operator fun invoke(): Result<List<Turn>>
}

class IListTurn @Inject constructor(
    private val equipRepository: EquipRepository,
    private val turnRepository: TurnRepository
): ListTurn {

    override suspend fun invoke(): Result<List<Turn>> =
        call(getClassAndMethod()) {
            val codTurnEquip = equipRepository.getCodTurnEquip().getOrThrow()
            turnRepository.listByCodTurnEquip(codTurnEquip = codTurnEquip).getOrThrow()
        }

}