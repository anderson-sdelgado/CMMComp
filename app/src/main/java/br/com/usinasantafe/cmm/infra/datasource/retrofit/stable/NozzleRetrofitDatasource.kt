package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.NozzleRetrofitModel

interface NozzleRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<NozzleRetrofitModel>>
}