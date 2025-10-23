package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.variable.CheckListApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.CheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelOutput
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRetrofitDatasource @Inject constructor(
    private val checkListApi: CheckListApi
): CheckListRetrofitDatasource {

    override suspend fun send(
        token: String,
        modelList: List<HeaderCheckListRetrofitModelOutput>
    ): Result<List<HeaderCheckListRetrofitModelInput>> {
        try {
            try {
                val response = checkListApi.send(
                    auth = token,
                    modelList = modelList
                )
                return Result.success(response.body()!!)
            } catch (e: Exception){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = e
                )
            }
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}