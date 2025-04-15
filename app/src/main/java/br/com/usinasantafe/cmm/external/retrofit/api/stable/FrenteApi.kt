package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FrenteRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_FRENTE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FrenteApi {

    @GET(WEB_ALL_FRENTE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<FrenteRetrofitModel>>

}
