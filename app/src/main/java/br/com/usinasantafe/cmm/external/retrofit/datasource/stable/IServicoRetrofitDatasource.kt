package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ServicoApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ServicoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServicoRetrofitModel
import javax.inject.Inject

class IServicoRetrofitDatasource @Inject constructor(
    private val servicoApi: ServicoApi
) : ServicoRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ServicoRetrofitModel>> {
        try {
            val response = servicoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IServicoRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
