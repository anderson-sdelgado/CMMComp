package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMenu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.MenuRepository
import br.com.usinasantafe.cmm.infra.datasource.internal.ItemMenuInternalDatasource
import br.com.usinasantafe.cmm.infra.models.internal.ItemMenuInternalModel
import br.com.usinasantafe.cmm.infra.models.internal.internalModelToEntity
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMenuRepository @Inject constructor(
    private val itemMenuInternalDatasource: ItemMenuInternalDatasource
): MenuRepository {

    override suspend fun listMenu(typeList: List<TypeItemMenu>): Result<List<ItemMenu>> {
        try{
            val result = itemMenuInternalDatasource.listAll()
            if(result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val modelList = result.getOrNull()!!
            val returnModelList = mutableListOf<ItemMenuInternalModel>()
            for(type in typeList) {
                returnModelList.addAll(
                    modelList.filter {
                        it.type == (type.ordinal + 1)
                    }
                )
            }
            return Result.success(
                returnModelList.map {
                    it.internalModelToEntity()
                }
            )
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}