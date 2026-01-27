package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.external.retrofit.api.variable.MotoMecApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelOutput
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMotoMecRetrofitDatasource @Inject constructor(
    private val motoMecApi: MotoMecApi
): MotoMecRetrofitDatasource {
    override suspend fun send(
        token: String,
        modelList: List<HeaderMotoMecRetrofitModelOutput>
    ): Result<List<HeaderMotoMecRetrofitModelInput>> =
        result(getClassAndMethod()) {
            motoMecApi.send(token, modelList).body()!!
        }
}