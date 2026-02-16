package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.NozzleRetrofitModel
import br.com.usinasantafe.cmm.lib.WEB_ALL_NOZZLE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface NozzleApi {

    @GET(WEB_ALL_NOZZLE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<NozzleRetrofitModel>>

}

