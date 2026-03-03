package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetSeqItem {
    suspend operator fun invoke(seqItem: Int): Result<Unit>
}

class ISetSeqItem @Inject constructor(
    private val mechanicRepository: MechanicRepository,
    private val motoMecRepository: MotoMecRepository,
    private val startWorkManager: StartWorkManager,
): SetSeqItem {

    override suspend fun invoke(seqItem: Int): Result<Unit> =
        call(getClassAndMethod()) {
            mechanicRepository.setSeqItem(seqItem).getOrThrow()
            val idHeader = motoMecRepository.getIdHeaderPointing().getOrThrow()
            mechanicRepository.save(idHeader).getOrThrow()
            startWorkManager()
        }

}