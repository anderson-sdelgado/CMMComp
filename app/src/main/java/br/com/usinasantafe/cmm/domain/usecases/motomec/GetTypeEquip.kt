package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetTypeEquip {
    suspend operator fun invoke(): Result<TypeEquip>
}

class IGetTypeEquip @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetTypeEquip {

    override suspend fun invoke(): Result<TypeEquip> =
        call(getClassAndMethod()) {
            motoMecRepository.getTypeEquipHeader().getOrThrow()
        }

}