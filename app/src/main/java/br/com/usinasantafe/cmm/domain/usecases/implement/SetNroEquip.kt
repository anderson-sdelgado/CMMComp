package br.com.usinasantafe.cmm.domain.usecases.implement

import br.com.usinasantafe.cmm.domain.entities.variable.Implement
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SetNroEquip {
    suspend operator fun invoke(
        nroEquip: String,
        pos: Int
    ): Result<Unit>
}

class ISetNroEquip @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetNroEquip {

    override suspend fun invoke(
        nroEquip: String,
        pos: Int
    ): Result<Unit> =
        call(getClassAndMethod()) {
            val nroEquipLong = runCatching {
                nroEquip.toLong()
            }.getOrElse { e ->
                throw Exception(::toLong.name, e)
            }
            val entity = Implement(
                nroEquip = nroEquipLong,
                pos = pos
            )
            motoMecRepository.setNroEquipImplement(entity).getOrThrow()
        }

}