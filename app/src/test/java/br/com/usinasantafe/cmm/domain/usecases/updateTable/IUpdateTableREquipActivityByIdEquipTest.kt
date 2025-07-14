package br.com.usinasantafe.cmm.domain.usecases.updateTable

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableREquipActivityByIdEquipTest {

    private val getToken = mock<GetToken>()
    private val rEquipActivityRepository = mock<REquipActivityRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val updateTableREquipActivityByIdEquip = IUpdateTableREquipActivityByIdEquip(
        getToken = getToken,
        rEquipActivityRepository = rEquipActivityRepository,
        configRepository = configRepository
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
            val result = updateTableREquipActivityByIdEquip(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableREquipActivityByIdEquip -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.get",
                    "-",
                    Exception()
                )
            )
            val result = updateTableREquipActivityByIdEquip(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableREquipActivityByIdEquip -> IConfigRepository.get -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in REquipActivityRepository getListByIdEquip`() =
        runTest {
            val config = Config(
                idEquip = 10
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            whenever(
                rEquipActivityRepository.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                resultFailure(
                    "IREquipActivityRepository.listByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = updateTableREquipActivityByIdEquip(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableREquipActivityByIdEquip -> IREquipActivityRepository.listByIdEquip -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in REquipActivityRepository deleteAll`() =
        runTest {
            val config = Config(
                idEquip = 10
            )
            val list = listOf(
                REquipActivity(
                    idREquipActivity = 1,
                    idActivity = 1,
                    idEquip = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            whenever(
                rEquipActivityRepository.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rEquipActivityRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IREquipActivityRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableREquipActivityByIdEquip(
                sizeAll = 7f,
                count = 1f
            )
            val resultList = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                resultList[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableREquipActivityByIdEquip -> IREquipActivityRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in REquipActivityRepository addAll`() =
        runTest {
            val config = Config(
                idEquip = 10
            )
            val list = listOf(
                REquipActivity(
                    idREquipActivity = 1,
                    idActivity = 1,
                    idEquip = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            whenever(
                rEquipActivityRepository.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rEquipActivityRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rEquipActivityRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "IREquipActivityRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableREquipActivityByIdEquip(
                sizeAll = 7f,
                count = 1f
            )
            val resultList = result.toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                resultList[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_equip_activity",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
//            assertEquals(
//                resultList[3],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "IUpdateTableREquipActivityByIdEquip -> IREquipActivityRepository.addAll -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
        }

}