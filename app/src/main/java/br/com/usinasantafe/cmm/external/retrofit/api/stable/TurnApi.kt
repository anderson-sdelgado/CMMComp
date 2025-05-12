package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_TURN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TurnApi {

    @GET(WEB_ALL_TURN)
    suspend fun all(@Header("Authorization") auth: String): Response<List<TurnRetrofitModel>>

}
