package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RFuncaoAtivParadaApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RFuncaoAtivParadaRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RFuncaoAtivParadaRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRFuncaoAtivParadaRetrofitDatasource @Inject constructor(
    private val rFuncaoAtivParadaApi: RFuncaoAtivParadaApi
) : RFuncaoAtivParadaRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<RFuncaoAtivParadaRetrofitModel>> {
        try {
            val response = rFuncaoAtivParadaApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
