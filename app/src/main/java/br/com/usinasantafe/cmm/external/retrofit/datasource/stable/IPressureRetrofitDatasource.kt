package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.PressureApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PressureRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressureRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IPressureRetrofitDatasource @Inject constructor(
    private val pressureApi: PressureApi
): PressureRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<PressureRetrofitModel>> =
        result(getClassAndMethod()) {
            pressureApi.all(token).body()!!
        }

}