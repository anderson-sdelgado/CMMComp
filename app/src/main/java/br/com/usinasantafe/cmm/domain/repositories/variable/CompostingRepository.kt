package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.lib.FlowComposting

interface CompostingRepository {
    suspend fun hasCompostingInputLoadSent(): Result<Boolean>
    suspend fun hasWill(flowComposting: FlowComposting): Result<Boolean>
}