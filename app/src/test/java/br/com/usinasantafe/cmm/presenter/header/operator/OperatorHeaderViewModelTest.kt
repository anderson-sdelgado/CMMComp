package br.com.usinasantafe.cmm.presenter.header.operator

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.header.CheckRegOperator
import br.com.usinasantafe.cmm.domain.usecases.header.SetRegOperator
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableColab
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TypeButton
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
class OperatorHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableColab = mock<UpdateTableColab>()
    private val checkRegOperator = mock<CheckRegOperator>()
    private val setRegOperator = mock<SetRegOperator>()
    private val viewModel = OperatorHeaderViewModel(
        updateTableColab = updateTableColab,
        checkRegOperator = checkRegOperator,
        setRegOperator = setRegOperator
    )

    @Test
    fun `setTextField - Check add char`() {
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            "1",
            viewModel.uiState.value.regColab
        )
    }

    @Test
    fun `setTextField - Check remover char`() {
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
            viewModel.uiState.value.regColab,
            "191"
        )
    }

    @Test
    fun `setTextField - Check msg of empty field`() {
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.FIELDEMPTY
        )
    }

    @Test
    fun `setTextField - Check return failure usecase if have error in usecase CleanColab`() =
        runTest {
            whenever(
                updateTableColab(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanColab -> java.lang.NullPointerException",
                        msgProgress = "CleanColab -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                OperatorHeaderState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                OperatorHeaderState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "OperatorViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "OperatorViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "OperatorViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `setTextField - Check return success in updateAllDatabase`() =
        runTest {
            whenever(
                updateTableColab(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_colab",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                OperatorHeaderState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                OperatorHeaderState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                OperatorHeaderState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(3f, 4f),
                )
            )
            assertEquals(
                result[3],
                OperatorHeaderState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
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
    fun `setTextField - Check return failure if field is empty`() =
        runTest {
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELDEMPTY
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OperatorViewModel.setTextField.OK -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in usecase CheckRegOperator`() =
        runTest {
            whenever(
                checkRegOperator("19759")
            ).thenReturn(
                resultFailure(
                    context = "ICheckRegOperator",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OperatorViewModel.setRegistrationOperator -> ICheckRegOperator -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return false if not have reg in table`() =
        runTest {
            whenever(
                checkRegOperator("19759")
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
                )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in usecase SetRegOperator`() =
        runTest {
            whenever(
                checkRegOperator("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setRegOperator("19759")
            ).thenReturn(
                resultFailure(
                    context = "ISetRegOperator",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OperatorViewModel.setRegistrationOperator -> ISetRegOperator -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check access release if executed successfully`() =
        runTest {
            whenever(
                checkRegOperator("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setRegOperator("19759")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }


}