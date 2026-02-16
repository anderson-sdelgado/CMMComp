package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.NozzleApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.NozzleRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.NozzleRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class INozzleRetrofitDatasource @Inject constructor(
    private val nozzleApi: NozzleApi
): NozzleRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<NozzleRetrofitModel>> =
        result(getClassAndMethod()) {
            nozzleApi.all(token).body()!!
        }

}