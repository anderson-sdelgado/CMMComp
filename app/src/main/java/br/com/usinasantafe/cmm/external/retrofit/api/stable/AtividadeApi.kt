package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.AtividadeRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_ATIVIDADE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AtividadeApi {

    @GET(WEB_ALL_ATIVIDADE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<AtividadeRetrofitModel>>

}