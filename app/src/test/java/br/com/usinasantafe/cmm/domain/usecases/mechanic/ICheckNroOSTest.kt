package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckNroOSTest {

    private val checkNetwork = mock<CheckNetwork>()
    private val equipRepository = mock<EquipRepository>()
    private val itemOSMechanicRepository = mock<ItemOSMechanicRepository>()
    private val getToken = mock<GetToken>()
    private val usecase = ICheckNroOS(
        checkNetwork = checkNetwork,
        equipRepository = equipRepository,
        itemOSMechanicRepository = itemOSMechanicRepository,
        getToken = getToken
    )

    @Test
    fun `Check return emit FAILURE_CONNECTION if checkNetwork return false`() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                false
            )
            val result = usecase("2200", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.FAILURE_CONNECTION,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun `Check return emit failure if input is invalid`() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            val result = usecase("2DFASF2DSFAS00", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ICheckNroOS -> toInt -> java.lang.NumberFormatException: For input string: \"2DFASF2DSFAS00\"",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun `Check return emit failure if have error in EquipRepository getIdEquipMain`() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                resultFailure(
                    context = "EquipRepository.getIdEquipMain",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase("2200", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ICheckNroOS -> EquipRepository.getIdEquipMain -> java.lang.Exception",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun `Check return emit failure if have error in GetToken`() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    context = "GetToken",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase("2200", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ICheckNroOS -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun `Check return failure TIME_OUT if have error SocketTimeoutException in ItemOSMechanicRepository listByIdEquipAndNroOS `() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 2200)
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRepository.listByIdEquipAndNroOS",
                    "-",
                    SocketTimeoutException()
                )
            )
            val result = usecase("2200", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.TIME_OUT,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemOSMechanicRepository listByIdEquipAndNroOS`() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 2200)
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRepository.listByIdEquipAndNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2200", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ICheckNroOS -> IItemOSMechanicRepository.listByIdEquipAndNroOS -> java.lang.Exception",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }


    @Test
    fun `Check return failure INVALID_VALUE if ItemOSMechanicRepository listByIdEquipAndNroOS return empty`() =
        runTest {
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 2200)
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = usecase("2200", sizeAll = 7f, count = 1f)
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CHECK_CONNECTION,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                        errors = Errors.INVALID,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }
}