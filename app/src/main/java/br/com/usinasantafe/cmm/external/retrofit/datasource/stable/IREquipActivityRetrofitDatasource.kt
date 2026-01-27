package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IREquipActivityRetrofitDatasource @Inject constructor(
    private val rEquipActivityApi: REquipActivityApi
) : REquipActivityRetrofitDatasource {
    override suspend fun listByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivityRetrofitModel>> =
        result(getClassAndMethod()) {
            rEquipActivityApi.getListByIdEquip(token, idEquip).body()!!
        }
}
