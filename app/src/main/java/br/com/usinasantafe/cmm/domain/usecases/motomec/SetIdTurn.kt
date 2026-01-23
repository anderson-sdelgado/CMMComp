package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdTurn {
    suspend operator fun invoke(id: Int): EmptyResult
}

class ISetIdTurn @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetIdTurn {

    override suspend fun invoke(id: Int): EmptyResult {
        return runCatching {
            motoMecRepository.setIdTurnHeader(id).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

}