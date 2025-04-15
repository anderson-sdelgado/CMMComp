package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.MotoMecRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_MOTOMEC
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MotoMecApi {

    @GET(WEB_ALL_MOTOMEC)
    suspend fun all(@Header("Authorization") auth: String): Response<List<MotoMecRetrofitModel>>

}
