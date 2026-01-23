package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckUpdateCheckListTest {

    private val itemCheckListRepository = mock<ItemCheckListRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val getToken = mock<GetToken>()
    private val usecase = ICheckUpdateCheckList(
        itemCheckListRepository = itemCheckListRepository,
        equipRepository = equipRepository,
        getToken = getToken
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getNroEquip`() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getNroEquipMain",
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
                "ICheckUpdateCheckList -> IEquipRepository.getNroEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in GetToken `() =
        runTest {
            whenever(
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "IGetToken.",
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
                "ICheckUpdateCheckList -> IGetToken."
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
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemCheckListRepository.checkUpdateByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
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
                equipRepository.getNroEquipMain()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemCheckListRepository.checkUpdateByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
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