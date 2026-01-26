package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedBytes.toInt
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
    ): Result<Boolean> {
        return runCatching {
            val nroEquipLong = runCatching {
                nroEquip.toLong()
            }.getOrElse { e ->
                throw Exception(::toLong.name, e)
            }
            equipRepository.hasEquipSecondary(
                nroEquip = nroEquipLong,
                typeEquip = typeEquip
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}