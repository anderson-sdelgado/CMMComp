package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel

interface ROSActivityRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ROSAtivRetrofitModel>>
    suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSAtivRetrofitModel>>
}
