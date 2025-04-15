package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ROSAtivApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSAtivRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel
import javax.inject.Inject

class IROSAtivRetrofitDatasource @Inject constructor(
    private val rosAtivApi: ROSAtivApi
) : ROSAtivRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ROSAtivRetrofitModel>> {
        try {
            val response = rosAtivApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IROSAtivRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
