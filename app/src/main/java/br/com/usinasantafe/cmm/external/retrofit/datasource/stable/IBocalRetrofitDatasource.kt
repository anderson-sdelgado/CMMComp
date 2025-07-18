package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.retrofit.api.stable.BocalApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.BocalRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.BocalRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IBocalRetrofitDatasource @Inject constructor(
    private val bocalApi: BocalApi
) : BocalRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<BocalRetrofitModel>> {
        try {
            val response = bocalApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
