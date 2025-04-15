package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RAtivParadaRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableRAtivParadaTest {

    private val getToken = mock<GetToken>()
    private val rAtivParadaRepository = mock<RAtivParadaRepository>()
    private val updateTableRAtivParada = IUpdateTableRAtivParada(
        getToken = getToken,
        rAtivParadaRepository = rAtivParadaRepository
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
            val result = updateTableRAtivParada(
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
                    msgProgress = "Recuperando dados da tabela tb_r_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RAtivParadaRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rAtivParadaRepository.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRAtivParada(
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
                    msgProgress = "Recuperando dados da tabela tb_r_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RAtivParadaRepository deleteAll`() =
        runTest {
            val list = listOf(
                RAtivParada(
                    idRAtivParada = 1,
                    idAtiv = 1,
                    idParada = 1,
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rAtivParadaRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rAtivParadaRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRAtivParada(
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
                    msgProgress = "Recuperando dados da tabela tb_r_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_ativ_parada",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in RAtivParadaRepository addAll`() =
        runTest {
            val list = listOf(
                RAtivParada(
                    idRAtivParada = 1,
                    idAtiv = 1,
                    idParada = 1,
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rAtivParadaRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rAtivParadaRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rAtivParadaRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableRAtivParada(
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
                    msgProgress = "Recuperando dados da tabela tb_r_ativ_parada do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_ativ_parada",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_ativ_parada",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableRAtivParada -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}