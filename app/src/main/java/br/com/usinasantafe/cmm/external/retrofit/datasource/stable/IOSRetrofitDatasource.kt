package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import javax.inject.Inject

class IOSRetrofitDatasource @Inject constructor(
    private val osApi: OSApi
) : OSRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<OSRetrofitModel>> {
        try {
            val response = osApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IOSRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
