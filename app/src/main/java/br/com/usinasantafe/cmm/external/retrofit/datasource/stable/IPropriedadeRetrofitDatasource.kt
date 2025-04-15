package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.PropriedadeApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PropriedadeRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PropriedadeRetrofitModel
import javax.inject.Inject

class IPropriedadeRetrofitDatasource @Inject constructor(
    private val propriedadeApi: PropriedadeApi
) : PropriedadeRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<PropriedadeRetrofitModel>> {
        try {
            val response = propriedadeApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IPropriedadeRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
