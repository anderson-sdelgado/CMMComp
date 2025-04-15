package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ColabRetrofitModel
import javax.inject.Inject

class IColabRetrofitDatasource @Inject constructor(
    private val colabApi: ColabApi
) : ColabRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>> {
        try {
            val response = colabApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IColabRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
