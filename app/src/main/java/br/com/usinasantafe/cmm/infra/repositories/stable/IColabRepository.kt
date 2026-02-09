package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ColabRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ColabRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.BaseStableRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IColabRepository @Inject constructor(
    private val colabRetrofitDatasource: ColabRetrofitDatasource,
    private val colabRoomDatasource: ColabRoomDatasource
) : BaseStableRepository<
            Colab,
            ColabRetrofitModel,
            ColabRoomModel
            >(
        retrofitListAll = colabRetrofitDatasource::listAll,
        roomAddAll = colabRoomDatasource::addAll,
        roomDeleteAll = colabRoomDatasource::deleteAll,
        retrofitToEntity = ColabRetrofitModel::retrofitModelToEntity,
        entityToRoom = Colab::entityToRoomModel,
    classAndMethod = getClassAndMethod()
    ),
    ColabRepository {

    override suspend fun hasByReg(reg: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            colabRoomDatasource.checkByReg(reg).getOrThrow()
        }
}
