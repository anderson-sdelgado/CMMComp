package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SaveNote {
    suspend operator fun invoke(
        idHeader: Int,
        idActivity: Int,
        nroOS: Int,
    ): Result<Unit>
}

class ISaveNote @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val performanceRepository: PerformanceRepository,
): SaveNote {

    override suspend fun invoke(
        idHeader: Int,
        idActivity: Int,
        nroOS: Int,
    ): Result<Unit> =
        call(getClassAndMethod()) {

            motoMecRepository.saveNote(idHeader).getOrThrow()

            val checkPerformance = functionActivityRepository.hasByIdAndType(
                idActivity = idActivity,
                typeActivity = TypeActivity.PERFORMANCE
            ).getOrThrow()

            if(checkPerformance) performanceRepository.initial(nroOS, idHeader).getOrThrow()

        }

}