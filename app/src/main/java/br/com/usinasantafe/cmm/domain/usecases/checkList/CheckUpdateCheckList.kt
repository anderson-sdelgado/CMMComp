package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface CheckUpdateCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckUpdateCheckList @Inject constructor(
    private val itemCheckListRepository: ItemCheckListRepository,
    private val configRepository: ConfigRepository,
): CheckUpdateCheckList {
    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetNroEquip = configRepository.getNroEquip()
            if (resultGetNroEquip.isFailure) {
                val e = resultGetNroEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckUpdateCheckList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val nroEquip = resultGetNroEquip.getOrNull()!!
            val resultCheckUpdateByNroEquip = itemCheckListRepository.checkUpdateByNroEquip(nroEquip)
            if (resultCheckUpdateByNroEquip.isFailure) {
                val e = resultCheckUpdateByNroEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckUpdateCheckList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val check = resultCheckUpdateByNroEquip.getOrNull()!!
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckUpdateCheckList",
                message = "-",
                cause = e
            )
        }
    }

}