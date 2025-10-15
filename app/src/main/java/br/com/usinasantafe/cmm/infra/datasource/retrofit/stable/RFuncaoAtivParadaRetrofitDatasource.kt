package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RFuncaoAtivParadaRetrofitModel

interface RFuncaoAtivParadaRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<RFuncaoAtivParadaRetrofitModel>>
}
