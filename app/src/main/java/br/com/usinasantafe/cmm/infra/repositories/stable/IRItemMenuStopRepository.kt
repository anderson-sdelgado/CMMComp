package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RItemMenuStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RItemMenuStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RItemMenuStopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.BaseStableRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IRItemMenuStopRepository @Inject constructor(
    private val rItemMenuStopRetrofitDatasource: RItemMenuStopRetrofitDatasource,
    private val rItemMenuStopRoomDatasource: RItemMenuStopRoomDatasource
) : BaseStableRepository<
        RItemMenuStop,
        RItemMenuStopRetrofitModel,
        RItemMenuStopRoomModel
        >(
    retrofitListAll = rItemMenuStopRetrofitDatasource::listAll,
    roomAddAll = rItemMenuStopRoomDatasource::addAll,
    roomDeleteAll = rItemMenuStopRoomDatasource::deleteAll,
    retrofitToEntity = RItemMenuStopRetrofitModel::retrofitModelToEntity,
    entityToRoom = RItemMenuStop::entityToRoomModel,
    classAndMethod = getClassAndMethod()
),
    RItemMenuStopRepository {

    override suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?> =
        call(getClassAndMethod()) {
        rItemMenuStopRoomDatasource.getIdStopByIdFunctionAndIdApp(idFunction, idApp).getOrThrow()
    }

}