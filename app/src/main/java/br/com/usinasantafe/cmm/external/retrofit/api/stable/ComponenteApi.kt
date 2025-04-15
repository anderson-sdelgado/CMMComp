package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponenteRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_COMPONENTE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ComponenteApi {

    @GET(WEB_ALL_COMPONENTE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ComponenteRetrofitModel>>

}
