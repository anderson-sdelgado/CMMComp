package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.TurnApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ITurnRetrofitDatasource @Inject constructor(
    private val turnApi: TurnApi
) : TurnoRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<TurnRetrofitModel>> {
        try {
            val response = turnApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
