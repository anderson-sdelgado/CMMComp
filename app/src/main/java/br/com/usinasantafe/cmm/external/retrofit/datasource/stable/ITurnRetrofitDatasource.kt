package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.TurnApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ITurnRetrofitDatasource @Inject constructor(
    private val turnApi: TurnApi
) : TurnRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<TurnRetrofitModel>> =
        result(getClassAndMethod()) {
            turnApi.all(token).body()!!
        }
}
