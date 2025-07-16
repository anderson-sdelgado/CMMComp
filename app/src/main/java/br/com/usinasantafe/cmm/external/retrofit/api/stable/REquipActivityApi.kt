package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface REquipActivityApi {

    @POST(WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP)
    suspend fun getListByIdEquip(
        @Header("Authorization") auth: String,
        @Body idEquip: Int
    ): Response<List<REquipActivityRetrofitModel>>

}
