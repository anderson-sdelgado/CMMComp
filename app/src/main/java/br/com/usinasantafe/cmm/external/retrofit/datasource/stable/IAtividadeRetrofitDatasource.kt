package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.AtividadeRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.AtividadeRetrofitModel
import javax.inject.Inject

class IAtividadeRetrofitDatasource @Inject constructor() : AtividadeRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<AtividadeRetrofitModel>> {
        TODO("Not yet implemented")
    }
}