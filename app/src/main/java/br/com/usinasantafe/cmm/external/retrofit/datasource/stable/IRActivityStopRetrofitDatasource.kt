package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RActivityStopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RActivityStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel
import javax.inject.Inject

class IRActivityStopRetrofitDatasource @Inject constructor(
    private val rActivityStopApi: RActivityStopApi
) : RActivityStopRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<RActivityStopRetrofitModel>> {
        try {
            val response = rActivityStopApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IRActivityStopRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
