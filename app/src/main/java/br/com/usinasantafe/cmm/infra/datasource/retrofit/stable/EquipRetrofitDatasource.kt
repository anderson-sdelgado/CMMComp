package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipRetrofitModel

interface EquipRetrofitDatasource {
    suspend fun getListByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<EquipRetrofitModel>>
}