package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IREquipActivityRetrofitDatasource @Inject constructor(
    private val rEquipActivityApi: REquipActivityApi
) : REquipActivityRetrofitDatasource {
    override suspend fun listByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivityRetrofitModel>> {
        try {
            val response = rEquipActivityApi.getListByIdEquip(
                auth = token,
                idEquip = idEquip
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
