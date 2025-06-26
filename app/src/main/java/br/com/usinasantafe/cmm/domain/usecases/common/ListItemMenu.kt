package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MenuRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.TypeView
import javax.inject.Inject

interface ListItemMenu {
    suspend operator fun invoke(): Result<List<ItemMenuModel>>
}

class IListItemMenu @Inject constructor(
    private val menuRepository: MenuRepository
): ListItemMenu {

    override suspend fun invoke(): Result<List<ItemMenuModel>> {
        try {
            val list: MutableList<TypeItemMenu> = mutableListOf()
            list.add(TypeItemMenu.ITEM_NORMAL)
            list.add(TypeItemMenu.BUTTON_FINISH_HEADER)
            val resultList = menuRepository.listMenu(list)
            if(resultList.isFailure){
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetItemMenuList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val listEntity = resultList.getOrNull()!!
            val itemList = listEntity.filter {
                it.type == TypeItemMenu.ITEM_NORMAL
            }.map { entity ->
                ItemMenuModel(
                    id = entity.id,
                    title = entity.title,
                    type = TypeView.ITEM
                )
            }
            val buttonItem = listEntity.filter {
                it.type == TypeItemMenu.BUTTON_FINISH_HEADER
            }.map { entity ->
                ItemMenuModel(
                    id = entity.id,
                    title = entity.title,
                    type = TypeView.BUTTON
                )
            }
            return Result.success(itemList + buttonItem)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetItemMenuList",
                message = "-",
                cause = e
            )
        }
    }

}