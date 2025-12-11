package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
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
    private val equipRepository: EquipRepository,
    private val getToken: GetToken
): CheckUpdateCheckList {
    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetNroEquip = equipRepository.getNroEquipMain()
            resultGetNroEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val nroEquip = resultGetNroEquip.getOrNull()!!
            val resultGetToken = getToken()
            resultGetToken.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultCheckUpdateByNroEquip = itemCheckListRepository.checkUpdateByNroEquip(
                token,
                nroEquip
            )
            resultCheckUpdateByNroEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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