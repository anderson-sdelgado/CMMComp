package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.PressaoBocalApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PressaoBocalRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressaoBocalRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IPressaoBocalRetrofitDatasource @Inject constructor(
    private val pressaoBocalApi: PressaoBocalApi
) : PressaoBocalRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<PressaoBocalRetrofitModel>> {
        try {
            val response = pressaoBocalApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
