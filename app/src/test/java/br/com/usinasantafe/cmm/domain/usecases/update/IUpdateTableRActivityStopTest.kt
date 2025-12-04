package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IUpdateTableRActivityStopTest {

    private val getToken = mock<GetToken>()
    private val rActivityStopRepository = mock<RActivityStopRepository>()
    private val updateTableRActivityStop = IUpdateTableRActivityStop(
        getToken = getToken,
        rActivityStopRepository = rActivityStopRepository
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
            val result = updateTableRActivityStop(
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
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RActivityStopRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rActivityStopRepository.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IRActivityStopRepository.recoverAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableRActivityStop(
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
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IRActivityStopRepository.recoverAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RActivityStopRepository deleteAll`() =
        runTest {
            val list = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 1,
                    idStop = 1,
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rActivityStopRepository.listAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rActivityStopRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IRActivityStopRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableRActivityStop(
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
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IRActivityStopRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RActivityStopRepository addAll`() =
        runTest {
            val list = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 1,
                    idStop = 1,
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rActivityStopRepository.listAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rActivityStopRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rActivityStopRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "IRActivityStopRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableRActivityStop(
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
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IRActivityStopRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}