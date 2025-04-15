package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_OS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface OSApi {

    @GET(WEB_ALL_OS)
    suspend fun all(@Header("Authorization") auth: String): Response<List<OSRetrofitModel>>

}
