package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipAtivRetrofitModel

interface REquipAtivRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<REquipAtivRetrofitModel>>
}
