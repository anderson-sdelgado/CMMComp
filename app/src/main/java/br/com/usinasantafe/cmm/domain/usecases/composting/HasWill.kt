package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.domain.repositories.variable.CompostingRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasWill {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasWill @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val compostingRepository: CompostingRepository
): HasWill {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val flow = motoMecRepository.getFlowCompostingHeader().getOrThrow()
            compostingRepository.hasWill(flow).getOrThrow()
        }

}