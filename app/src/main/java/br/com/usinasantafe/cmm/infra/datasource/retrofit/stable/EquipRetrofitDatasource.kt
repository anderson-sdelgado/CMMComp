package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipMainRetrofitModel

interface EquipRetrofitDatasource {
    suspend fun listAll(
        token: String
    ): Result<List<EquipMainRetrofitModel>>
}