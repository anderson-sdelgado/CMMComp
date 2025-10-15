package br.com.usinasantafe.cmm.external.internal

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.internal.ItemMenuInternalDatasource
import br.com.usinasantafe.cmm.infra.models.internal.ItemMenuInternalModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.itemMenuDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class IItemMenuInternalDatasource  @Inject constructor(
): ItemMenuInternalDatasource {

    override suspend fun listAll(): Result<List<ItemMenuInternalModel>> {
        try {
            val gson = Gson()
            val itemTypeActivity = object : TypeToken<List<ItemMenuInternalModel>>() {}.type
            val internalModelList = gson.fromJson<List<ItemMenuInternalModel>>(itemMenuDatabase, itemTypeActivity)
            return Result.success(internalModelList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}