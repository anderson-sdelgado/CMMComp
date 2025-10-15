package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipPneuRetrofitModel

interface REquipPneuRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<REquipPneuRetrofitModel>>
}
