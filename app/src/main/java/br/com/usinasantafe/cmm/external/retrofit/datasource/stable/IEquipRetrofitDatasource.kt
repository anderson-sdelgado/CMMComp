package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.EquipApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipRetrofitModel
import javax.inject.Inject

class IEquipRetrofitDatasource @Inject constructor(
    private val api: EquipApi
): EquipRetrofitDatasource {

    override suspend fun recoverAll(
        token: String,
        idEquip: Int
    ): Result<List<EquipRetrofitModel>> {
        try {
            val response = api.all(
                auth = token,
                idEquip = idEquip
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}