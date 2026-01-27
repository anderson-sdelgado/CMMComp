package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IConfigRetrofitDatasource @Inject constructor(
    private val configApi: ConfigApi,
): ConfigRetrofitDatasource {

    override suspend fun recoverToken(
        retrofitModelOutput: ConfigRetrofitModelOutput
    ): Result<ConfigRetrofitModelInput> =
        result(getClassAndMethod()) {
            configApi.send(retrofitModelOutput).body()!!
        }

}