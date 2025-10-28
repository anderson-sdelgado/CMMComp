package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.cmm.external.retrofit.api.stable.FunctionActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionActivityRetrofitDatasource @Inject constructor(
    private val functionActivityApi: FunctionActivityApi
): FunctionActivityRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<FunctionActivityRetrofitModel>> {
        try {
            val response = functionActivityApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}