package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemMenuPMMApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuPMMRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuPMMRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemMenuPMMRetrofitDatasource @Inject constructor(
    private val itemMenuPMMApi: ItemMenuPMMApi
): ItemMenuPMMRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ItemMenuPMMRetrofitModel>> {
        try {
            val response = itemMenuPMMApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}