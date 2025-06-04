package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.StopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import javax.inject.Inject

class IStopRetrofitDatasource @Inject constructor(
    private val stopApi: StopApi
) : StopRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<StopRetrofitModel>> {
        try {
            val response = stopApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IStopRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
