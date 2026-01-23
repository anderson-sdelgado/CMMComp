package br.com.usinasantafe.cmm.presenter.view.header.turnList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListTurn
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdTurn
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TurnListHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listTurn = mock<ListTurn>()
    private val setIdTurn = mock<SetIdTurn>()
    private val updateTableTurn = mock<UpdateTableTurn>()
    private val viewModel = TurnListHeaderViewModel(
        listTurn = listTurn,
        setIdTurn = setIdTurn,
        updateTableTurn = updateTableTurn
    )

    @Test
    fun `turnList - Check return failure if have error in GetTurnList`() =
        runTest {
            whenever(
                listTurn()
            ).thenReturn(
                resultFailure(
                    context = "GetTurnList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.turnList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TurnListHeaderViewModel.turnList -> GetTurnList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `turnList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listTurn()
            ).thenReturn(
                Result.success(
                    listOf(
                        Turn(
                            idTurn = 1,
                            codTurnEquip = 1,
                            nroTurn = 1,
                            descrTurn = "TURNO 1"
                        ),
                        Turn(
                            idTurn = 2,
                            codTurnEquip = 1,
                            nroTurn = 2,
                            descrTurn = "TURNO 2"
                        )
                    )
                )
            )
            viewModel.turnList()
            val list = viewModel.uiState.value.turnList
            assertEquals(
                list.count(),
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idTurn,
                1
            )
            assertEquals(
                entity1.codTurnEquip,
                1
            )
            assertEquals(
                entity1.nroTurn,
                1
            )
            assertEquals(
                entity1.descrTurn,
                "TURNO 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idTurn,
                2
            )
            assertEquals(
                entity2.codTurnEquip,
                1
            )
            assertEquals(
                entity2.nroTurn,
                2
            )
            assertEquals(
                entity2.descrTurn,
                "TURNO 2"
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableTurn`() =
        runTest {
            whenever(
                updateTableTurn(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableTurn -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                TurnListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                TurnListHeaderState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "TurnListHeaderViewModel.updateAllDatabase -> UpdateTableTurn -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TurnListHeaderViewModel.updateDatabase -> TurnListHeaderViewModel.updateAllDatabase -> UpdateTableTurn -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTableTurn(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_turn",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                TurnListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                TurnListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                TurnListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_turn",
                    currentProgress = percentage(3f, 4f),
                    )
            )
            assertEquals(
                result[3],
                TurnListHeaderState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
        }

    @Test
    fun `setIdTurnHeader - Check return failure if have error in SetIdTurn`() =
        runTest {
            whenever(
                setIdTurn(
                    id = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "SetIdTurn",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setIdTurnHeader(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TurnListHeaderViewModel.setIdTurnHeader -> SetIdTurn -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `setIdTurnHeader - Check access release if function execute successfully`() =
        runTest {
            whenever(
                setIdTurn(
                    id = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.setIdTurnHeader(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}