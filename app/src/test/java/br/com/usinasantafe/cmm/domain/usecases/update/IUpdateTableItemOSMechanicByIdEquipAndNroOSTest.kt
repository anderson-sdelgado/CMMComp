package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.Int
import kotlin.test.assertEquals

class IUpdateTableItemOSMechanicByIdEquipAndNroOSTest {

    private val getToken = mock<GetToken>()
    private val equipRepository = mock<EquipRepository>()
    private val mechanicRepository = mock<MechanicRepository>()
    private val itemOSMechanicRepository = mock<ItemOSMechanicRepository>()
    private val usecase = IUpdateTableItemOSMechanicByIdEquipAndNroOS(
        getToken = getToken,
        equipRepository = equipRepository,
        mechanicRepository = mechanicRepository,
        itemOSMechanicRepository = itemOSMechanicRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "GetToken",
                    "-",
                    Exception()
                )
            )
            val list = usecase(7f, 1f).toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
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
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdEquipMain`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdEquipMain",
                    "-",
                    Exception()
                )
            )
            val list = usecase(7f, 1f).toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
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
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IEquipRepository.getIdEquipMain -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in MechanicRepository getNroOS`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.getNroOS",
                    "-",
                    Exception()
                )
            )
            val list = usecase(7f, 1f).toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
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
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IMechanicRepository.getNroOS -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemOSMechanicRepository listByIdEquipAndNroOS`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 123456)
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRepository.listByIdEquipAndNroOS",
                    "-",
                    Exception()
                )
            )
            val list = usecase(7f, 1f).toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
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
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IItemOSMechanicRepository.listByIdEquipAndNroOS -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemOSMechanicRepository deleteAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 123456)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 2200,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            )
            whenever(
                itemOSMechanicRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val list = usecase(7f, 1f).toList()
            assertEquals(
                list.count(),
                3
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
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
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IItemOSMechanicRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemOSMechanicRepository addAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 123456)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 2200,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            )
            whenever(
                itemOSMechanicRepository.addAll(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 2200,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val list = usecase(7f, 1f).toList()
            verify(itemOSMechanicRepository, atLeastOnce()).deleteAll()
            assertEquals(
                list.count(),
                4
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IItemOSMechanicRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS("token", 1, 123456)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 2200,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            )
            val list = usecase(7f, 1f).toList()
            verify(itemOSMechanicRepository, atLeastOnce()).deleteAll()
            verify(itemOSMechanicRepository, atLeastOnce()).addAll(
                listOf(
                    ItemOSMechanic(
                        id = 1,
                        idEquip = 1,
                        nroOS = 2200,
                        seqItem = 1,
                        idServ = 1,
                        idComp = 1
                    )
                )
            )
            assertEquals(
                list.count(),
                3
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }

}