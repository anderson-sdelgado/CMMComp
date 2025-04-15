package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RAtivParadaApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RAtivParadaRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RAtivParadaRetrofitModel
import javax.inject.Inject

class IRAtivParadaRetrofitDatasource @Inject constructor(
    private val rAtivParadaApi: RAtivParadaApi
) : RAtivParadaRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<RAtivParadaRetrofitModel>> {
        try {
            val response = rAtivParadaApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IRAtivParadaRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
