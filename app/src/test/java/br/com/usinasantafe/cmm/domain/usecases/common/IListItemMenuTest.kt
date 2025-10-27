package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MenuRepository
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.TypeView
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class IListItemMenuTest {

    private val menuRepository = Mockito.mock<MenuRepository>()
    private val usecase = IListItemMenu(
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
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IGetItemMenuList"
            )
            Assert.assertEquals(
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
                        ItemMenuPMM(
                            id = 1,
                            title = "TRABALHANDO",
                            type = TypeItemMenu.ITEM_NORMAL
                        ),
                        ItemMenuPMM(
                            id = 2,
                            title = "PARADO",
                            type = TypeItemMenu.ITEM_NORMAL
                        ),
                        ItemMenuPMM(
                            id = 3,
                            title = "FINALIZAR BOLETIM",
                            type = TypeItemMenu.BUTTON_FINISH_HEADER
                        )
                    )
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            val listEntity = result.getOrNull()!!
            Assert.assertEquals(
                listEntity.size,
                3
            )
            val entity1 = listEntity[0]
            Assert.assertEquals(
                entity1.id,
                1
            )
            Assert.assertEquals(
                entity1.title,
                "TRABALHANDO"
            )
            Assert.assertEquals(
                entity1.type,
                TypeView.ITEM
            )
            val entity2 = listEntity[1]
            Assert.assertEquals(
                entity2.id,
                2
            )
            Assert.assertEquals(
                entity2.title,
                "PARADO"
            )
            Assert.assertEquals(
                entity2.type,
                TypeView.ITEM
            )
            val entity3 = listEntity[2]
            Assert.assertEquals(
                entity3.id,
                3
            )
            Assert.assertEquals(
                entity3.title,
                "FINALIZAR BOLETIM"
            )
            Assert.assertEquals(
                entity3.type,
                TypeView.BUTTON
            )
        }

}