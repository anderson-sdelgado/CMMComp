package br.com.usinasantafe.cmm.external.retrofit.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.DatasourceException
import br.com.usinasantafe.cmm.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelOutput
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
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ConfigRetrofitDatasource.recoverToken",
                    cause = e
                )
            )
        }
    }

}