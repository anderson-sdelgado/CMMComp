package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel

interface REquipActivityRetrofitDatasource {
    suspend fun getListByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivityRetrofitModel>>
}
