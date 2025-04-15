package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.AtividadeApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.AtividadeRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.AtividadeRetrofitModel
import javax.inject.Inject

class IAtividadeRetrofitDatasource @Inject constructor(
    private val atividadeApi: AtividadeApi
) : AtividadeRetrofitDatasource {
    override suspend fun recoverAll(
        token: String
    ): Result<List<AtividadeRetrofitModel>> {
        try {
            val response = atividadeApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IAtividadeRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}