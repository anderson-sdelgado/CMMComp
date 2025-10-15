package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityStopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.TypeOper
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableRFuncaoAtivStopTest {

    private val getToken = mock<GetToken>()
    private val functionActivityStopRepository = mock<FunctionActivityStopRepository>()
    private val updateTableFunctionActivityStop = IUpdateTableFunctionActivityStop(
        getToken = getToken,
        functionActivityStopRepository = functionActivityStopRepository
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
            val result = updateTableFunctionActivityStop(
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
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableFunctionActivityStop -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityStopRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                functionActivityStopRepository.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityStopRepository.recoverAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableFunctionActivityStop(
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
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableFunctionActivityStop -> IFunctionActivityStopRepository.recoverAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in UpdateTableFunctionActivityStop deleteAll`() =
        runTest {
            val list = listOf(
                RFuncaoAtivParada(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 1,
                    funcAtiv = FuncAtividade.LEIRA,
                    tipoFuncao = TypeOper.PARADA,
                    funcParada = FuncParada.CHECKLIST
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                functionActivityStopRepository.listAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                functionActivityStopRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityStopRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableFunctionActivityStop(
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
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableFunctionActivityStop -> IFunctionActivityStopRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityStopRepository addAll`() =
        runTest {
            val list = listOf(
                RFuncaoAtivParada(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 1,
                    funcAtiv = FuncAtividade.LEIRA,
                    tipoFuncao = TypeOper.PARADA,
                    funcParada = FuncParada.CHECKLIST
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                functionActivityStopRepository.listAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                functionActivityStopRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionActivityStopRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityStopRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableFunctionActivityStop(
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
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_function_activity_stop",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableFunctionActivityStop -> IFunctionActivityStopRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}