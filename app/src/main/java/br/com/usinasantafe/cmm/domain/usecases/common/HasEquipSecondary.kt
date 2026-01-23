package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasEquipSecondary {
    suspend operator fun invoke(
        nroEquip: String,
        typeEquip: TypeEquip
    ): Result<Boolean>
}

class IHasEquipSecondary @Inject constructor(
    private val equipRepository: EquipRepository
): HasEquipSecondary {

    override suspend fun invoke(
        nroEquip: String,
        typeEquip: TypeEquip
    ): Result<Boolean> {
        try {
            val result = equipRepository.hasEquipSecondary(
                nroEquip = nroEquip.toLong(),
                typeEquip = typeEquip
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}