package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckUpdateCheckListTest {

    private val itemCheckListRepository = mock<ItemCheckListRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = ICheckUpdateCheckList(
        itemCheckListRepository = itemCheckListRepository,
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getNroEquip`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckUpdateCheckList -> IConfigRepository.getNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository checkUpdateByNroEquip`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemCheckListRepository.checkUpdateByNroEquip(1L)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.checkUpdateByNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckUpdateCheckList -> ICheckListRepository.checkUpdateByNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(1L)
            )

            whenever(
                itemCheckListRepository.checkUpdateByNroEquip(1L)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }


}