package br.com.usinasantafe.cmm.presenter.view.common.os

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.config.GetApp
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNroOS
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetNroOSHeader
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroOS
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableOS
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableROSActivity
import br.com.usinasantafe.cmm.lib.App
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.view.motomec.note.stopList.StopListNoteState
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class OSCommonViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val hasNroOS = mock<HasNroOS>()
    private val setNroOS = mock<SetNroOS>()
    private val getNroOSHeader = mock<GetNroOSHeader>()
    private val getApp = mock<GetApp>()
    private val updateTableOS = mock<UpdateTableOS>()
    private val updateTableROSActivity = mock<UpdateTableROSActivity>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
            )
        )
    ) = OSCommonViewModel(
        savedStateHandle,
        hasNroOS = hasNroOS,
        setNroOS = setNroOS,
        getNroOSHeader = getNroOSHeader,
        getApp = getApp,
        updateTableOS = updateTableOS,
        updateTableROSActivity = updateTableROSActivity
    )

    @Test
    fun `setTextField - Check add char`() {
        val viewModel = createViewModel()
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroOS,
            "1"
        )
    }

    @Test
    fun `setTextField - Check remover char`() {
        val viewModel = createViewModel()
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroOS,
            "191"
        )
    }

    @Test
    fun `start - Check return failure if have error in GetNroOSHeader - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    context = "IGetNroOSHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
            viewModel.start()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "OSCommonViewModel.start -> IGetNroOSHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `start - Check return failure if have error in GetApp - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                getNroOSHeader()
            ).thenReturn(
                Result.success("10000")
            )
            whenever(
                getApp()
            ).thenReturn(
                resultFailure(
                    context = "IGetApp",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
            viewModel.start()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "OSCommonViewModel.start -> IGetApp -> java.lang.Exception"
            )
        }

    @Test
    fun `start - Check return true if GetApp execute successfully - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                getNroOSHeader()
            ).thenReturn(
                Result.success("10000")
            )
            whenever(
                getApp()
            ).thenReturn(
                Result.success(App.ECM)
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
            viewModel.start()
            assertEquals(
                viewModel.uiState.value.app,
                App.ECM
            )
            assertEquals(
                viewModel.uiState.value.nroOS,
                "10000"
            )
        }

    @Test
    fun `start - Check return true if GetApp execute successfully - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                getApp()
            ).thenReturn(
                Result.success(App.ECM)
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
                    )
                )
            )
            viewModel.start()
            assertEquals(
                viewModel.uiState.value.app,
                App.ECM
            )
            assertEquals(
                viewModel.uiState.value.nroOS,
                ""
            )
        }

    @Test
    fun `start - Check return true if GetApp execute successfully - FlowApp PRE_CEC`() =
        runTest {
            whenever(
                getApp()
            ).thenReturn(
                Result.success(App.ECM)
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.PRE_CEC.ordinal,
                    )
                )
            )
            viewModel.start()
            assertEquals(
                viewModel.uiState.value.app,
                App.ECM
            )
            assertEquals(
                viewModel.uiState.value.nroOS,
                ""
            )
        }

    @Test
    fun `set - Check msg of empty field`() {
        val viewModel = createViewModel()
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.status.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.status.errors,
            Errors.FIELD_EMPTY
        )
    }

    @Test
    fun `set - Check return failure if have error in HasNroOS`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                resultFailure(
                    context = "IHasNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "OSCommonViewModel.setTextField -> OSCommonViewModel.set -> IHasNroOS -> java.lang.Exception"
                )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check return invalid if data is not found`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `set - Check return failure if have error in SetNroOS`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS(
                    nroOS = "123456"
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "OSCommonViewModel.setTextField -> OSCommonViewModel.set -> ISetNroOS -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS("123456")
            ).thenReturn(
                Result.success(Unit)
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
                )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableROSActivity`() =
        runTest {
            whenever(
                updateTableROSActivity(
                    count = 1f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(1f, 7f)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableROSActivity -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(1f, 7f)
                    )
                )
            )
            assertEquals(
                result[1],
                OSCommonState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSCommonViewModel.updateAllDatabase -> UpdateTableROSActivity -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    ),
                )
            )
            viewModel.setTextField(
                "",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "OSCommonViewModel.setTextField -> OSCommonViewModel.updateAllDatabase -> UpdateTableROSActivity -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableStop`() =
        runTest {
            whenever(
                updateTableROSActivity(
                    count = 1f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(1f, 7f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(2f, 7f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )
            whenever(
                updateTableOS(
                    count = 2f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(4f, 7f)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableOS -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                5
            )
            assertEquals(
                result[0],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(1f, 7f)
                    )
                )
            )
            assertEquals(
                result[1],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(2f, 7f)
                    )
                )
            )
            assertEquals(
                result[2],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(3f, 7f)
                    )
                )
            )
            assertEquals(
                result[3],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(4f, 7f)
                    )
                )
            )
            assertEquals(
                result[4],
                OSCommonState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "OSCommonViewModel.updateAllDatabase -> UpdateTableOS -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.setTextField(
                "",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "OSCommonViewModel.setTextField -> OSCommonViewModel.updateAllDatabase -> UpdateTableOS -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTableROSActivity(
                    count = 1f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(1f, 7f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(2f, 7f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )
            whenever(
                updateTableOS(
                    count = 2f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(4f, 7f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(5f, 7f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(6f, 7f)
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                7
            )
            assertEquals(
                result[0],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(1f, 7f)
                    )
                )
            )
            assertEquals(
                result[1],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(2f, 7f)
                    )
                )
            )
            assertEquals(
                result[2],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_os_activity",
                        currentProgress = percentage(3f, 7f)
                    )
                )
            )
            assertEquals(
                result[3],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(4f, 7f)
                    )
                )
            )
            assertEquals(
                result[4],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(5f, 7f)
                    )
                )
            )
            assertEquals(
                result[5],
                OSCommonState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(6f, 7f)
                    )
                )
            )
            assertEquals(
                result[6],
                OSCommonState(
                    status = UpdateStatusState(
                        flagDialog = true,
                        flagProgress = false,
                        flagFailure = false,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.setTextField(
                "",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.levelUpdate,
                LevelUpdate.FINISH_UPDATE_COMPLETED
            )
        }

}