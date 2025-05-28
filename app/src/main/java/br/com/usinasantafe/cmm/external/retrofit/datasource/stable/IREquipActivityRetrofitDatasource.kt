package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import javax.inject.Inject

class IREquipActivityRetrofitDatasource @Inject constructor(
    private val rEquipActivityApi: REquipActivityApi
) : REquipActivityRetrofitDatasource {
    override suspend fun getListByIdEquip(
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
            return resultFailure(
                context = "IREquipActivityRetrofitDatasource.getListByIdEquip",
                message = "-",
                cause = e
            )
        }
    }
}
