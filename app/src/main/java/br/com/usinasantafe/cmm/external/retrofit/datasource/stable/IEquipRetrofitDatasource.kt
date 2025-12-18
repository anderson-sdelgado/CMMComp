package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.EquipApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipSecondaryRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRetrofitDatasource @Inject constructor(
    private val equipApi: EquipApi
): EquipRetrofitDatasource {

    override suspend fun listAll(
        token: String
    ): Result<List<EquipSecondaryRetrofitModel>> {
        try {
            val response = equipApi.all(
                auth = token
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}