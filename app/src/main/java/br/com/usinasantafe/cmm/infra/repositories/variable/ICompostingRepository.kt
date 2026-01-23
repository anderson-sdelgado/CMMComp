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
        val result = inputCompostingRoomDatasource.hasSentLoad()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun hasWill(flowComposting: FlowComposting): Result<Boolean> {
        when(flowComposting){
            FlowComposting.INPUT -> {
                val result = inputCompostingRoomDatasource.hasWill()
                result.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                return result
            }
            FlowComposting.COMPOUND -> {
                val result = compoundCompostingRoomDatasource.hasWill()
                result.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                return result
            }
        }
    }
}