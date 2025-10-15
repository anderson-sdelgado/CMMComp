package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckUpdateCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckUpdateCheckList @Inject constructor(
    private val itemCheckListRepository: ItemCheckListRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): CheckUpdateCheckList {
    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetNroEquip = configRepository.getNroEquip()
            if (resultGetNroEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetNroEquip.exceptionOrNull()!!
                )
            }
            val nroEquip = resultGetNroEquip.getOrNull()!!
            val resultGetToken = getToken()
            if (resultGetToken.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetToken.exceptionOrNull()!!
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultCheckUpdateByNroEquip = itemCheckListRepository.checkUpdateByNroEquip(
                token,
                nroEquip
            )
            if (resultCheckUpdateByNroEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckUpdateByNroEquip.exceptionOrNull()!!
                )
            }
            val check = resultCheckUpdateByNroEquip.getOrNull()!!
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}