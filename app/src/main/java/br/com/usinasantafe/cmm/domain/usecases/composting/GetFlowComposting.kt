package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetFlowComposting {
    suspend operator fun invoke(): Result<FlowComposting>
}

class IGetFlowComposting @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetFlowComposting {

    override suspend fun invoke(): Result<FlowComposting> {
        val result = motoMecRepository.getFlowCompostingHeader()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}