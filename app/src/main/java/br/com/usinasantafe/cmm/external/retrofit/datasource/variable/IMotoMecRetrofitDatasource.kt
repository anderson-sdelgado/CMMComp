package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.variable.MotoMecApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelOutput
import javax.inject.Inject

class IMotoMecRetrofitDatasource @Inject constructor(
    private val motoMecApi: MotoMecApi
): MotoMecRetrofitDatasource {
    override suspend fun send(
        token: String,
        modelList: List<HeaderMotoMecRetrofitModelOutput>
    ): Result<List<HeaderMotoMecRetrofitModelInput>> {
        try {
            val response = motoMecApi.send(
                auth = token,
                modelList = modelList
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IMotoMecRetrofitDatasource.send",
                message = "-",
                cause = e
            )
        }
    }
}