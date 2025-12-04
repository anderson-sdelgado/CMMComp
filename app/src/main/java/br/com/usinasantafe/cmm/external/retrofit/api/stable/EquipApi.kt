package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipRetrofitModel

import br.com.usinasantafe.cmm.lib.WEB_EQUIP_LIST_BY_ID_EQUIP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface EquipApi {

    @POST(WEB_EQUIP_LIST_BY_ID_EQUIP)
    suspend fun getListByIdEquip(
        @Header("Authorization") auth: String,
        @Body idEquip: Int
    ): Response<List<EquipRetrofitModel>>

}