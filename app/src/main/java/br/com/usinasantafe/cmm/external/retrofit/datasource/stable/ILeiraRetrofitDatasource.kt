package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.LeiraApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.LeiraRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.LeiraRetrofitModel
import javax.inject.Inject

class ILeiraRetrofitDatasource @Inject constructor(
    private val leiraApi: LeiraApi
) : LeiraRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<LeiraRetrofitModel>> {
        try {
            val response = leiraApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "ILeiraRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
