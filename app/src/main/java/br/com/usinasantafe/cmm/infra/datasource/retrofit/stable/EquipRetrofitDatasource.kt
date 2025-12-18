package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipSecondaryRetrofitModel

interface EquipRetrofitDatasource {
    suspend fun listAll(
        token: String
    ): Result<List<EquipSecondaryRetrofitModel>>
}