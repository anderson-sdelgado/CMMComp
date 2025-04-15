package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipPneuApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipPneuRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipPneuRetrofitModel
import javax.inject.Inject

class IREquipPneuRetrofitDatasource @Inject constructor(
    private val rEquipPneuApi: REquipPneuApi
) : REquipPneuRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<REquipPneuRetrofitModel>> {
        try {
            val response = rEquipPneuApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IREquipPneuRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
