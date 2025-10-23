package br.com.usinasantafe.cmm.infra.datasource.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelOutput

interface CheckListRetrofitDatasource {
    suspend fun send(
        token: String,
        modelList: List<HeaderCheckListRetrofitModelOutput>
    ): Result<List<HeaderCheckListRetrofitModelInput>>
}