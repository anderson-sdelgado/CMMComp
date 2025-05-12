package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel

interface OSRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<OSRetrofitModel>>
    suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<OSRetrofitModel>>
}
