package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel

interface TurnoRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<TurnRetrofitModel>>
}
