package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface RefreshHeaderPointing {
    suspend operator fun invoke(id: Int): Result<Unit>
}

class IRefreshHeaderPointing @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): RefreshHeaderPointing {

    override suspend fun invoke(id: Int): Result<Unit> =
        call(getClassAndMethod()) {
            motoMecRepository.openHeaderById(id)
        }

}