package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListTurn {
    suspend operator fun invoke(): Result<List<Turn>>
}

class IListTurn @Inject constructor(
    private val equipRepository: EquipRepository,
    private val turnRepository: TurnRepository
): ListTurn {

    override suspend fun invoke(): Result<List<Turn>> {
        try {
            val resultGetCodTurnEquip = equipRepository.getCodTurnEquip()
            resultGetCodTurnEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val codTurnEquip = resultGetCodTurnEquip.getOrNull()!!
            val resultGetTurnList = turnRepository.listByCodTurnEquip(
                codTurnEquip = codTurnEquip
            )
            resultGetTurnList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return resultGetTurnList
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}