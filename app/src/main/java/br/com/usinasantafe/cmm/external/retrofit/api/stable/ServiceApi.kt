package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServiceRetrofitModel
import br.com.usinasantafe.cmm.lib.WEB_ALL_SERVICE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ServiceApi {

    @GET(WEB_ALL_SERVICE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ServiceRetrofitModel>>

}