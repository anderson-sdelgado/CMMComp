package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ROSActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IROSActivityRetrofitDatasource @Inject constructor(
    private val rosActivityApi: ROSActivityApi
) : ROSActivityRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<ROSActivityRetrofitModel>> {
        try {
            val response = rosActivityApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivityRetrofitModel>> {
        try {
            val response = rosActivityApi.getListByNroOS(
                auth = token,
                nroOS = nroOS
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
