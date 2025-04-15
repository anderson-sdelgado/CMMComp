package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ParadaRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_PARADA
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ParadaApi {

    @GET(WEB_ALL_PARADA)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ParadaRetrofitModel>>

}
