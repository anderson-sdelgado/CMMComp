package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SaveNote {
    suspend operator fun invoke(): Result<Unit>
}

class ISaveNote @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val performanceRepository: PerformanceRepository,
    private val fertigationRepository: FertigationRepository
): SaveNote {

    override suspend fun invoke(): Result<Unit> =
        call(getClassAndMethod()) {

            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
            val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()

            motoMecRepository.saveNote(idHeader, nroOS, idActivity).getOrThrow()

            val checkPerformance = functionActivityRepository.hasByIdAndType(
                idActivity = idActivity,
                typeActivity = TypeActivity.PERFORMANCE
            ).getOrThrow()

            if(checkPerformance) performanceRepository.initial(nroOS, idHeader).getOrThrow()

            val checkCollection = functionActivityRepository.hasByIdAndType(
                idActivity = idActivity,
                typeActivity = TypeActivity.COLLECTION
            ).getOrThrow()

            if(checkCollection) fertigationRepository.initialCollection(nroOS, idHeader).getOrThrow()

        }

}