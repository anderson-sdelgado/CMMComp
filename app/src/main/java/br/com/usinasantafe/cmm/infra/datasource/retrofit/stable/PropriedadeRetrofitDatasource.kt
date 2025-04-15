package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PropriedadeRetrofitModel

interface PropriedadeRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<PropriedadeRetrofitModel>>
}
