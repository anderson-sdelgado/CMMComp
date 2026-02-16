package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IColabRepository @Inject constructor(
    private val colabRetrofitDatasource: ColabRetrofitDatasource,
    private val colabRoomDatasource: ColabRoomDatasource
) : ColabRepository {

    override suspend fun addAll(list: List<Colab>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            colabRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            colabRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<Colab>> =
        call(getClassAndMethod()) {
            val modelList = colabRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun hasByReg(reg: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            colabRoomDatasource.checkByReg(reg).getOrThrow()
        }
}
