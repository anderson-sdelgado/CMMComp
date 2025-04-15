package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnoRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_TURNO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TurnoApi {

    @GET(WEB_ALL_TURNO)
    suspend fun all(@Header("Authorization") auth: String): Response<List<TurnoRetrofitModel>>

}
