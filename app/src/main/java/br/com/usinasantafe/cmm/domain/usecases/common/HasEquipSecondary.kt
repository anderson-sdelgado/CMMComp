package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedInts.toLong
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
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            val nroEquipLong = tryCatch(::toLong.name) {
                nroEquip.toLong()
            }
            equipRepository.hasEquipSecondary(nroEquipLong, typeEquip).getOrThrow()
        }

}