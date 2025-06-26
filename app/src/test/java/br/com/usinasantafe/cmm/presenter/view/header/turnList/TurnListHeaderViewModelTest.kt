package br.com.usinasantafe.cmm.presenter.view.header.turnList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.ListTurn
import br.com.usinasantafe.cmm.domain.usecases.header.SetIdTurn
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableTurn
import br.com.usinasantafe.cmm.utils.Errors
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
                "TurnListViewModel.turnList -> GetTurnList -> java.lang.Exception"
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
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_turn",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableTurn -> java.lang.NullPointerException",
                        msgProgress = "UpdateTableTurn -> java.lang.NullPointerException",
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
                    msgProgress = "Limpando a tabela tb_turn",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                TurnListHeaderState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "TurnListViewModel.updateAllDatabase -> UpdateTableTurn -> java.lang.NullPointerException",
                    msgProgress = "TurnListViewModel.updateAllDatabase -> UpdateTableTurn -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "TurnListViewModel.updateAllDatabase -> UpdateTableTurn -> java.lang.NullPointerException"
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
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_turn",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_turn do Web Service",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_turn",
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
                    msgProgress = "Limpando a tabela tb_turn",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                TurnListHeaderState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_turn do Web Service",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                TurnListHeaderState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_turn",
                    currentProgress = percentage(3f, 4f),
                    )
            )
            assertEquals(
                result[3],
                TurnListHeaderState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "Atualização de dados realizado com sucesso!"
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
                "TurnListViewModel.setIdTurnHeader -> SetIdTurn -> java.lang.Exception"
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
                Result.success(true)
            )
            viewModel.setIdTurnHeader(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}