package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipPneuRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_EQUIP_PNEU
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface REquipPneuApi {

    @GET(WEB_ALL_R_EQUIP_PNEU)
    suspend fun all(@Header("Authorization") auth: String): Response<List<REquipPneuRetrofitModel>>

}
