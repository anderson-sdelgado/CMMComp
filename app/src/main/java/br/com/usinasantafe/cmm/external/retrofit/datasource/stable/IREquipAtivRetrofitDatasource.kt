package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipAtivApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipAtivRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipAtivRetrofitModel
import javax.inject.Inject

class IREquipAtivRetrofitDatasource @Inject constructor(
    private val rEquipAtivApi: REquipAtivApi
) : REquipAtivRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<REquipAtivRetrofitModel>> {
        try {
            val response = rEquipAtivApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IREquipAtivRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
