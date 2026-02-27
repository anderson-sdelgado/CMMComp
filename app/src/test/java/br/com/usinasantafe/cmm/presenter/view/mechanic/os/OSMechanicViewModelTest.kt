package br.com.usinasantafe.cmm.presenter.view.mechanic.os

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.mechanic.SetNroOS
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class OSMechanicViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkNroOS = mock<CheckNroOS>()
    private val setNroOS = mock<SetNroOS>()

    private val viewModel = OSMechanicViewModel(
        checkNroOS = checkNroOS,
        setNroOS = setNroOS
    )

    @Test
    fun `set - Check return failure if have error in CheckNroOS initial`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICheckNroOS -> IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
            viewModel.setTextField("123456", TypeButton.NUMERIC)
            val list = viewModel.checkAndUpdate().toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[1],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSMechanicViewModel.checkAndUpdate -> ICheckNroOS -> IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value,
                OSMechanicState(
                    nroOS = "123456",
                    flagAccess = false,
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSMechanicViewModel.setTextField -> OSMechanicViewModel.set -> OSMechanicViewModel.checkAndUpdate -> ICheckNroOS -> IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
        }

    @Test
    fun `set - Check return failure if have error in CheckNroOS`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
            viewModel.setTextField("123456", TypeButton.NUMERIC)
            val list = viewModel.checkAndUpdate().toList()
            assertEquals(
                list.size,
                3
            )
            assertEquals(
                list[0],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[1],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[2],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSMechanicViewModel.checkAndUpdate -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value,
                OSMechanicState(
                    nroOS = "123456",
                    flagAccess = false,
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSMechanicViewModel.setTextField -> OSMechanicViewModel.set -> OSMechanicViewModel.checkAndUpdate -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
        }

    @Test
    fun `set - Check return failure if have error in SetNroOS`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
            whenever(
                setNroOS("123456")
            ).thenReturn(
                resultFailure(
                    context = "SetNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField("123456", TypeButton.NUMERIC)
            val list = viewModel.checkAndUpdate().toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[1],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value,
                OSMechanicState(
                    nroOS = "123456",
                    flagAccess = false,
                    status = UpdateStatusState(
                        flagProgress = false,
                        errors = Errors.EXCEPTION,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSMechanicViewModel.setTextField -> OSMechanicViewModel.set -> SetNroOS -> java.lang.Exception",
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = 1f
                    )
                )
            )
        }

    @Test
    fun `set - Check access if process execute successfully`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
            viewModel.setTextField("123456", TypeButton.NUMERIC)
            val list = viewModel.checkAndUpdate().toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[1],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value,
                OSMechanicState(
                    nroOS = "123456",
                    flagAccess = true,
                    status = UpdateStatusState(
                        flagProgress = false,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
        }

    @Test
    fun `set - Check access and failure if have Errors TIME_OUT in CheckNroOS`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    ),
                    UpdateStatusState(
                        flagProgress = false,
                        errors = Errors.TIME_OUT,
                        flagDialog = false,
                        flagFailure = true,
                        failure = "",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
            viewModel.setTextField("123456", TypeButton.NUMERIC)
            val list = viewModel.checkAndUpdate().toList()
            assertEquals(
                list.size,
                3
            )
            assertEquals(
                list[0],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CHECK_CONNECTION,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(1f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[1],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = updatePercentage(2f, 1f, 5f)
                    )
                )
            )
            assertEquals(
                list[2],
                OSMechanicState(
                    nroOS = "123456",
                    status = UpdateStatusState(
                        flagProgress = false,
                        errors = Errors.TIME_OUT,
                        flagDialog = false,
                        flagFailure = true,
                        failure = "",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value,
                OSMechanicState(
                    nroOS = "123456",
                    flagAccess = true,
                    status = UpdateStatusState(
                        flagProgress = false,
                        errors = Errors.TIME_OUT,
                        flagDialog = false,
                        flagFailure = true,
                        failure = "",
                        currentProgress = 1f,
                        levelUpdate = null
                    )
                )
            )
        }


}