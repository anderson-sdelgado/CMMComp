package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.retrofit.api.stable.MotoMecApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.MotoMecRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMotoMecRetrofitDatasource @Inject constructor(
    private val motoMecApi: MotoMecApi
) : MotoMecRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<MotoMecRetrofitModel>> {
        try {
            val response = motoMecApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
