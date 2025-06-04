package br.com.usinasantafe.cmm.domain.usecases.note

import br.com.usinasantafe.cmm.domain.entities.view.ItemMenu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MenuRepository
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.TypeView
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetItemMenuListTest {

    private val menuRepository = mock<MenuRepository>()
    private val usecase = IGetItemMenuList(
        menuRepository = menuRepository
    )

    @Test
    fun `Check return failure if have error in MenuRepository listMenu`() =
        runTest {
            val list: MutableList<TypeItemMenu> = mutableListOf()
            list.add(TypeItemMenu.ITEM_NORMAL)
            list.add(TypeItemMenu.BUTTON_FINISH_HEADER)
            whenever(
                menuRepository.listMenu(list)
            ).thenReturn(
                resultFailure(
                    context = "IGetItemMenuList",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemMenuList -> IGetItemMenuList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val list: MutableList<TypeItemMenu> = mutableListOf()
            list.add(TypeItemMenu.ITEM_NORMAL)
            list.add(TypeItemMenu.BUTTON_FINISH_HEADER)
            whenever(
                menuRepository.listMenu(list)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenu(
                            id = 1,
                            title = "TRABALHANDO",
                            type = TypeItemMenu.ITEM_NORMAL
                        ),
                        ItemMenu(
                            id = 2,
                            title = "PARADO",
                            type = TypeItemMenu.ITEM_NORMAL
                        ),
                        ItemMenu(
                            id = 3,
                            title = "FINALIZAR BOLETIM",
                            type = TypeItemMenu.BUTTON_FINISH_HEADER
                        )
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listEntity = result.getOrNull()!!
            assertEquals(
                listEntity.size,
                3
            )
            val entity1 = listEntity[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.title,
                "TRABALHANDO"
            )
            assertEquals(
                entity1.type,
                TypeView.ITEM
            )
            val entity2 = listEntity[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.title,
                "PARADO"
            )
            assertEquals(
                entity2.type,
                TypeView.ITEM
            )
            val entity3 = listEntity[2]
            assertEquals(
                entity3.id,
                3
            )
            assertEquals(
                entity3.title,
                "FINALIZAR BOLETIM"
            )
            assertEquals(
                entity3.type,
                TypeView.BUTTON
            )
        }

}