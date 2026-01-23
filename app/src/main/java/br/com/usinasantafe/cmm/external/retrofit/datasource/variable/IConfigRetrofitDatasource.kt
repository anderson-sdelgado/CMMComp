package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IConfigRetrofitDatasource @Inject constructor(
    private val configApi: ConfigApi,
): ConfigRetrofitDatasource {

    override suspend fun recoverToken(
        retrofitModelOutput: ConfigRetrofitModelOutput
    ): Result<ConfigRetrofitModelInput> {
        try {
            val response = configApi.send(
                retrofitModelOutput
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}