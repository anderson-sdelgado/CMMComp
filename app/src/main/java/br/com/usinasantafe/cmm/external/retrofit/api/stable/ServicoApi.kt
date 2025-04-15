package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServicoRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_SERVICO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ServicoApi {

    @GET(WEB_ALL_SERVICO)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ServicoRetrofitModel>>

}
