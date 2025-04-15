package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipAtivRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_EQUIP_ATIV
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface REquipAtivApi {

    @GET(WEB_ALL_R_EQUIP_ATIV)
    suspend fun all(@Header("Authorization") auth: String): Response<List<REquipAtivRetrofitModel>>

}
