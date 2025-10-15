package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSActivityRetrofitModel

interface ROSActivityRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ROSActivityRetrofitModel>>
    suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivityRetrofitModel>>
}
