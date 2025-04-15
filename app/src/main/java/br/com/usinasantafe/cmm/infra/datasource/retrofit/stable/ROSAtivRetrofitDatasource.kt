package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel

interface ROSAtivRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ROSAtivRetrofitModel>>
}
