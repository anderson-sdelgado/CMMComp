package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.internal.ItemMenuInternalDatasource
import br.com.usinasantafe.cmm.infra.models.internal.ItemMenuInternalModel
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.TypeView
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IMenuRepositoryTest {

    private val itemMenuInternalDatasource = mock<ItemMenuInternalDatasource>()
    private val repository = IMenuRepository(
        itemMenuInternalDatasource = itemMenuInternalDatasource
    )

    @Test
    fun `listMenu - Check return failure if have error in ItemMenuInternalDatasource`() =
        runTest {
            whenever(
                itemMenuInternalDatasource.listAll()
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuInternalDatasource.listAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val list: MutableList<TypeItemMenu> = mutableListOf()
            list.add(TypeItemMenu.ITEM_NORMAL)
            list.add(TypeItemMenu.BUTTON_FINISH_HEADER)
            val result = repository.listMenu(list)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMenuRepository.listMenu -> IItemMenuInternalDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listMenu - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemMenuInternalDatasource.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuInternalModel(
                            id = 1,
                            title = "Item 1",
                            type = 1
                        ),
                        ItemMenuInternalModel(
                            id = 2,
                            title = "Item 2",
                            type = 2
                        )
                    )
                )
            )
            val list: MutableList<TypeItemMenu> = mutableListOf()
            list.add(TypeItemMenu.ITEM_NORMAL)
            list.add(TypeItemMenu.BUTTON_FINISH_HEADER)
            val result = repository.listMenu(list)
            assertEquals(
                result.isSuccess,
                true
            )
            val listEntity = result.getOrNull()!!
            assertEquals(
                listEntity.size,
                2
            )
            val item1 = listEntity[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.title,
                "Item 1"
            )
            assertEquals(
                item1.type,
                TypeItemMenu.ITEM_NORMAL
            )
            val item2 = listEntity[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.title,
                "Item 2"
            )
            assertEquals(
                item2.type,
                TypeItemMenu.BUTTON_FINISH_HEADER
            )
        }

}