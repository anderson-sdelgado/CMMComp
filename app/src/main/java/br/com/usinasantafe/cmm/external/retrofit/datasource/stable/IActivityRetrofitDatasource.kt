package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IActivityRetrofitDatasource @Inject constructor(
    private val activityApi: ActivityApi
) : ActivityRetrofitDatasource {

    override suspend fun listAll(
        token: String
    ): Result<List<ActivityRetrofitModel>> {
        try {
            val response = activityApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}