package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ComponenteApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ComponenteRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponenteRetrofitModel
import javax.inject.Inject

class IComponenteRetrofitDatasource @Inject constructor(
    private val componenteApi: ComponenteApi
) : ComponenteRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ComponenteRetrofitModel>> {
        try {
            val response = componenteApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IComponenteRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
