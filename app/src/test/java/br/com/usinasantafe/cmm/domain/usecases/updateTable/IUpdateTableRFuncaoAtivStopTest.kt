package br.com.usinasantafe.cmm.domain.usecases.updateTable

import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RFuncaoAtivParadaRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
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
    private val rFuncaoAtivParadaRepository = mock<RFuncaoAtivParadaRepository>()
    private val updateTableRFuncaoAtivParada = IUpdateTableFunctionActivityStop(
        getToken = getToken,
        rFuncaoAtivParadaRepository = rFuncaoAtivParadaRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRFuncaoAtivParada(
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
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RFuncaoAtivParadaRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rFuncaoAtivParadaRepository.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRFuncaoAtivParada(
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
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RFuncaoAtivParadaRepository deleteAll`() =
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
                rFuncaoAtivParadaRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rFuncaoAtivParadaRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRFuncaoAtivParada(
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
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_funcao_ativ_parada",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RFuncaoAtivParadaRepository addAll`() =
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
                rFuncaoAtivParadaRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rFuncaoAtivParadaRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rFuncaoAtivParadaRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRFuncaoAtivParada(
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
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_funcao_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_funcao_ativ_parada",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_funcao_ativ_parada",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRFuncaoAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}