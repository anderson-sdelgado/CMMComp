package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressureRetrofitModel
import br.com.usinasantafe.cmm.lib.WEB_ALL_PRESSURE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PressureApi {

    @GET(WEB_ALL_PRESSURE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<PressureRetrofitModel>>

}