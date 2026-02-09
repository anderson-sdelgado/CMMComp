package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionStopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.utils.BaseStableRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IFunctionStopRepository @Inject constructor(
    private val functionStopRetrofitDatasource: FunctionStopRetrofitDatasource,
    private val functionStopRoomDatasource: FunctionStopRoomDatasource
) : BaseStableRepository<
        FunctionStop,
        FunctionStopRetrofitModel,
        FunctionStopRoomModel
        >(
    retrofitListAll = functionStopRetrofitDatasource::listAll,
    roomAddAll = functionStopRoomDatasource::addAll,
    roomDeleteAll = functionStopRoomDatasource::deleteAll,
    retrofitToEntity = FunctionStopRetrofitModel::retrofitModelToEntity,
    entityToRoom = FunctionStop::entityToRoomModel,
    classAndMethod = getClassAndMethod()
),
    FunctionStopRepository {

    override suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?> =
        call(getClassAndMethod()) {
            functionStopRoomDatasource.getIdStopByType(typeStop).getOrThrow()
        }

}