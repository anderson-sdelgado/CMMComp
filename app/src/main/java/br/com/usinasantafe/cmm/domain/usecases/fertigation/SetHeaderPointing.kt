package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetHeaderPointing {
    suspend operator fun invoke(id: Int): Result<Unit>
}

class ISetHeaderPointing @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
): SetHeaderPointing {

    override suspend fun invoke(id: Int): Result<Unit> =
        call(getClassAndMethod()) {
            motoMecRepository.openHeaderById(id).getOrThrow()
        }

}