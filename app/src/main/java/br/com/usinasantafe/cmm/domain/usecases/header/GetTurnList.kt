package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface GetTurnList {
    suspend operator fun invoke(): Result<List<Turn>>
}

class IGetTurnList @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository,
    private val turnRepository: TurnRepository
): GetTurnList {

    override suspend fun invoke(): Result<List<Turn>> {
        try {
            val resultGetConfig = configRepository.get()
            if (resultGetConfig.isFailure) {
                val e = resultGetConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetTurnList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultGetCodTurnEquip = equipRepository.getCodTurnEquipByIdEquip(
                idEquip = config.idEquip!!
            )
            if (resultGetCodTurnEquip.isFailure) {
                val e = resultGetCodTurnEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetTurnList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val codTurnEquip = resultGetCodTurnEquip.getOrNull()!!
            val resultGetTurnList = turnRepository.getListByCodTurnEquip(
                codTurnEquip = codTurnEquip
            )
            if (resultGetTurnList.isFailure) {
                val e = resultGetTurnList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetTurnList",
                    message = e.message,
                    cause = e.cause
                )
            }
            return resultGetTurnList
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetTurnList",
                message = "-",
                cause = e
            )
        }
    }

}