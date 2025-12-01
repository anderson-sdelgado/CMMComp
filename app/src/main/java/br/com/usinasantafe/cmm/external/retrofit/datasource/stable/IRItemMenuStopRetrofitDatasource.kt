package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RItemMenuStopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RItemMenuStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RItemMenuStopRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRItemMenuStopRetrofitDatasource @Inject constructor(
    private val rItemMenuStopApi: RItemMenuStopApi
): RItemMenuStopRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<RItemMenuStopRetrofitModel>> {
        try {
            val response = rItemMenuStopApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}