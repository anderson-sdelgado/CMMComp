package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ROSActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel
import javax.inject.Inject

class IROSActivityRetrofitDatasource @Inject constructor(
    private val rosActivityApi: ROSActivityApi
) : ROSActivityRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ROSAtivRetrofitModel>> {
        try {
            val response = rosActivityApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IROSActivityRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSAtivRetrofitModel>> {
        try {
            val response = rosActivityApi.getListByNroOS(
                auth = token,
                nroOS = nroOS
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IROSActivityRetrofitDatasource.getListByNroOS",
                message = "-",
                cause = e
            )
        }
    }
}
