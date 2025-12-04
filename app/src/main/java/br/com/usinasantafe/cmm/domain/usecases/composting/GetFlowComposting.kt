package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.lib.FlowComposting
import javax.inject.Inject

interface GetFlowComposting {
    suspend operator fun invoke(): Result<FlowComposting>
}

class IGetFlowComposting @Inject constructor(
): GetFlowComposting {

    override suspend fun invoke(): Result<FlowComposting> {
        TODO("Not yet implemented")
    }

}