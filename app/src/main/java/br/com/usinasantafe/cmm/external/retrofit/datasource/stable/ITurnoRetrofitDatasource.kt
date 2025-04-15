package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.TurnoApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnoRetrofitModel
import javax.inject.Inject

class ITurnoRetrofitDatasource @Inject constructor(
    private val turnoApi: TurnoApi
) : TurnoRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<TurnoRetrofitModel>> {
        try {
            val response = turnoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "ITurnoRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
