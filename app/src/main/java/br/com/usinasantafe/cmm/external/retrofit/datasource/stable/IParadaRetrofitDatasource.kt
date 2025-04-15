package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ParadaApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ParadaRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ParadaRetrofitModel
import javax.inject.Inject

class IParadaRetrofitDatasource @Inject constructor(
    private val paradaApi: ParadaApi
) : ParadaRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ParadaRetrofitModel>> {
        try {
            val response = paradaApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IParadaRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
