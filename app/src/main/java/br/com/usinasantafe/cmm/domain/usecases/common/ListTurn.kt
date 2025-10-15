package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListTurn {
    suspend operator fun invoke(): Result<List<Turn>>
}

class IListTurn @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository,
    private val turnRepository: TurnRepository
): ListTurn {

    override suspend fun invoke(): Result<List<Turn>> {
        try {
            val resultGetConfig = configRepository.get()
            if (resultGetConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetConfig.exceptionOrNull()!!
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultGetCodTurnEquip = equipRepository.getCodTurnEquipByIdEquip(
                idEquip = config.idEquip!!
            )
            if (resultGetCodTurnEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetCodTurnEquip.exceptionOrNull()!!
                )
            }
            val codTurnEquip = resultGetCodTurnEquip.getOrNull()!!
            val resultGetTurnList = turnRepository.listByCodTurnEquip(
                codTurnEquip = codTurnEquip
            )
            if (resultGetTurnList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetTurnList.exceptionOrNull()!!
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