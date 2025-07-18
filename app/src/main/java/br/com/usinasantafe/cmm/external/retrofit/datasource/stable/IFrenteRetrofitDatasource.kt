package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.retrofit.api.stable.FrenteApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FrenteRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FrenteRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFrenteRetrofitDatasource @Inject constructor(
    private val frenteApi: FrenteApi
) : FrenteRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<FrenteRetrofitModel>> {
        try {
            val response = frenteApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
