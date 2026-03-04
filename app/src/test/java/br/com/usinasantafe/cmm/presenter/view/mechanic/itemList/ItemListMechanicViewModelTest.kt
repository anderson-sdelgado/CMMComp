package br.com.usinasantafe.cmm.presenter.view.mechanic.itemList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.mechanic.ListItemOS
import br.com.usinasantafe.cmm.domain.usecases.mechanic.SetSeqItem
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemOSMechanicByIdEquipAndNroOS
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.model.ItemOSMechanicModel
import br.com.usinasantafe.cmm.presenter.view.configuration.config.ConfigState
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ItemListMechanicViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listItemOS = mock<ListItemOS>()
    private val updateTableItemOSMechanicByIdEquipAndNroOS = mock<UpdateTableItemOSMechanicByIdEquipAndNroOS>()
    private val updateTableService = mock<UpdateTableService>()
    private val updateTableComponent = mock<UpdateTableComponent>()
    private val setSeqItem = mock<SetSeqItem>()

    private val viewModel = ItemListMechanicViewModel(
        listItemOS = listItemOS,
        updateTableItemOSMechanicByIdEquipAndNroOS = updateTableItemOSMechanicByIdEquipAndNroOS,
        updateTableService = updateTableService,
        updateTableComponent = updateTableComponent,
        setSeqItem = setSeqItem
    )
    private val sizeAll = (3f * 3) + 1f
    private val tableList = listOf(
        "tb_item_os_mechanic", "tb_component", "tb_service"
    )

    @Test
    fun `list - Check return failure if have error in ListItemOS`() =
        runTest {
            whenever(
                listItemOS()
            ).thenReturn(
                resultFailure(
                    context = "ListItemOS",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.list()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ItemListMechanicViewModel.list -> ListItemOS -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `list - Check return true if ListItemOS execute successfully`() =
        runTest {
            whenever(
                listItemOS()
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanicModel(
                            seq = 1,
                            service = "Service 1",
                            component = "Component 1",
                        )
                    )
                )
            )
            viewModel.list()
            val list = viewModel.uiState.value.list
            assertEquals(
                list.size,
                1
            )
            val entity1 = list[0]
            assertEquals(
                entity1.seq,
                1
            )
            assertEquals(
                entity1.service,
                "Service 1"
            )
            assertEquals(
                entity1.component,
                "Component 1"
            )
        }

    @Test
    fun `set - Check return failure if have error in SetSeqItem`() =
        runTest {
            whenever(
                setSeqItem(1)
            ).thenReturn(
                resultFailure(
                    context = "SetSeqItem",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.set(1)
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ItemListMechanicViewModel.set -> SetSeqItem -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check return true if SetSeqItem execute successfully`() =
        runTest {
            viewModel.set(1)
            verify(setSeqItem, atLeastOnce()).invoke(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableItemOSMechanicByIdEquipAndNroOS`() =
        runTest {
            val qtdBefore = 0f
            whenever(
                updateTableItemOSMechanicByIdEquipAndNroOS(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanItemOSMechanic -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_os_mechanic",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ItemListMechanicViewModel.updateAllDatabase -> CleanItemOSMechanic -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableComponent`() =
        runTest {
            val qtdBefore = 1f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableComponent(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanComponent -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ItemListMechanicViewModel.updateAllDatabase -> CleanComponent -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableService`() =
        runTest {
            val qtdBefore = 2f
            wheneverSuccess(qtdBefore)
            whenever(
                updateTableService(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanService -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    )
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ItemListMechanicViewModel.updateAllDatabase -> CleanService -> java.lang.NullPointerException",
                    )
                )
            )
        }

    @Test
    fun `update - Check return true if Update execute successfully`() =
        runTest {
            val qtdBefore = 3f
            wheneverSuccess(qtdBefore)
            val result = viewModel.updateAllDatabase().toList()
            checkResultUpdate(qtdBefore, result)
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 1).toInt()
            )
            assertEquals(
                result[((qtdBefore * 3)).toInt()],
                ItemListMechanicState(
                    status = UpdateStatusState(
                        flagProgress = false,
                        errors = Errors.FIELD_EMPTY,
                        flagDialog = true,
                        flagFailure = false,
                        failure = "",
                        currentProgress = 1f,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED
                    )
                )
            )
        }


    private fun wheneverSuccess(posTable: Float) =
        runTest {
            var contUpdate = 0f
            var contWhenever = 0f

            val updateFunctions = listOf<
                    suspend (Float, Float) -> Flow<UpdateStatusState>
                    >(
                { sizeAll, count -> updateTableItemOSMechanicByIdEquipAndNroOS(sizeAll, count) },
                { sizeAll, count -> updateTableComponent(sizeAll, count) },
                { sizeAll, count -> updateTableService(sizeAll, count) },
            )

            for(func in updateFunctions) {
                whenever(
                    func(
                        sizeAll,
                        ++contUpdate
                    )
                ).thenReturn(
                    flowOf(
                        UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.RECOVERY,
                            tableUpdate = tableList[contUpdate.toInt() - 1],
                            currentProgress = percentage(++contWhenever, sizeAll)
                        ),
                        UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.CLEAN,
                            tableUpdate = tableList[contUpdate.toInt() - 1],
                            currentProgress = percentage(++contWhenever, sizeAll)
                        ),
                        UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.SAVE,
                            tableUpdate = tableList[contUpdate.toInt() - 1],
                            currentProgress = percentage(++contWhenever, sizeAll)
                        ),
                    )
                )
                if(posTable == contUpdate) break
            }
        }

    private fun checkResultUpdate(posTable: Float, result: List<ItemListMechanicState>) =
        runTest {
            var contUpdate = 0f
            var cont = 0
            for(table in tableList) {
                assertEquals(
                    result[cont++],
                    ItemListMechanicState(
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.RECOVERY,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                assertEquals(
                    result[cont++],
                    ItemListMechanicState(
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.CLEAN,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                assertEquals(
                    result[cont++],
                    ItemListMechanicState(
                        status = UpdateStatusState(
                            flagProgress = true,
                            levelUpdate = LevelUpdate.SAVE,
                            tableUpdate = table,
                            currentProgress = percentage(cont.toFloat(), sizeAll)
                        )
                    )
                )
                ++contUpdate
                if(posTable == contUpdate) break
            }
        }

}