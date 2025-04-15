package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ColabRetrofitModel

interface ColabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>>
}
