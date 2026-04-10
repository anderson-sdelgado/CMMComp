package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SetNroEquipReel {
    suspend operator fun invoke(
        nroEquip: String
    ): Result<Unit>
}

class ISetNroEquipReel @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): SetNroEquipReel {

    override suspend fun invoke(
        nroEquip: String
    ): Result<Unit> =
        call(getClassAndMethod()) {
            val nroEquipLong = tryCatch(::toLong.name) {
                nroEquip.toLong()
            }
            val idEquip = equipRepository.getIdByNro(nroEquipLong).getOrThrow()
            motoMecRepository.setDataEquipHeader(idEquip = idEquip, typeEquip = TypeEquip.REEL_FERT).getOrThrow()
        }

}