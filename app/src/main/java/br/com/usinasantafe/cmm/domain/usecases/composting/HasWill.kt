package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CompostingRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasWill {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasWill @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val compostingRepository: CompostingRepository
): HasWill {

    override suspend fun invoke(): Result<Boolean> {
        val resultGetFlow = motoMecRepository.getFlowCompostingHeader()
        resultGetFlow.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val flow = resultGetFlow.getOrThrow()
        val result = compostingRepository.hasWill(flow)
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}