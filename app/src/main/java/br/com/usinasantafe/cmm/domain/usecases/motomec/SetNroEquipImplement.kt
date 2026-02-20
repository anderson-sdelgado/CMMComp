package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroEquipImplement {
    suspend operator fun invoke(
        nroEquip: String,
        pos: Int
    ): Result<Unit>
}

class ISetNroEquipImplement @Inject constructor(
): SetNroEquipImplement {

    override suspend fun invoke(
        nroEquip: String,
        pos: Int
    ): Result<Unit> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}