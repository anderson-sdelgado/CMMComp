package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CompostingRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CompoundCompostingRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.InputCompostingRoomDatasource
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICompostingRepository @Inject constructor(
    private val inputCompostingRoomDatasource: InputCompostingRoomDatasource,
    private val compoundCompostingRoomDatasource: CompoundCompostingRoomDatasource
): CompostingRepository {
    override suspend fun hasCompostingInputLoadSent(): Result<Boolean> {
        return runCatching {
            inputCompostingRoomDatasource.hasSentLoad().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasWill(flowComposting: FlowComposting): Result<Boolean> {
        return runCatching {
            when(flowComposting){
                FlowComposting.INPUT -> {
                    inputCompostingRoomDatasource.hasWill().getOrThrow()
                }
                FlowComposting.COMPOUND -> {
                    compoundCompostingRoomDatasource.hasWill().getOrThrow()
                }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }
}