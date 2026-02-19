package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckCloseCollection {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckCloseCollection @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val fertigationRepository: FertigationRepository
): CheckCloseCollection {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val check = !fertigationRepository.hasCollectionByIdHeaderAndValueNull(id).getOrThrow()
            if(check) motoMecRepository.finishHeader().getOrThrow()
            return@call check
        }

}