package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.FunctionStopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionStopRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionStopRetrofitDatasource @Inject constructor(
    private val functionStopApi: FunctionStopApi
): FunctionStopRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<FunctionStopRetrofitModel>> {
        try {
            val response = functionStopApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}