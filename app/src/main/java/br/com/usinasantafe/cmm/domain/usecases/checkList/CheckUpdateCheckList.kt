package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
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
        return runCatching {
            val nroEquip = equipRepository.getNroEquipMain().getOrThrow()
            val token = getToken().getOrThrow()
            itemCheckListRepository.checkUpdateByNroEquip(
                token,
                nroEquip
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}