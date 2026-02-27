package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.cmm.lib.WEB_ALL_OS
import br.com.usinasantafe.cmm.lib.WEB_OS_LIST_BY_NRO_OS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OSApi {

    @GET(WEB_ALL_OS)
    suspend fun all(@Header("Authorization") auth: String): Response<List<OSRetrofitModel>>

    @POST(WEB_OS_LIST_BY_NRO_OS)
    suspend fun listByNroOS(
        @Header("Authorization") auth: String,
        @Body nroOS: Int
    ): Response<List<OSRetrofitModel>>

}
