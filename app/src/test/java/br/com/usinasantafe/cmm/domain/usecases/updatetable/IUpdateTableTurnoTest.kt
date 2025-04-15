package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Turno
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnoRepository
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

class IUpdateTableTurnoTest {

    private val getToken = mock<GetToken>()
    private val turnoRepository = mock<TurnoRepository>()
    private val updateTableTurno = IUpdateTableTurno(
        getToken = getToken,
        turnoRepository = turnoRepository
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
            val result = updateTableTurno(
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
                    msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in TurnoRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                turnoRepository.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableTurno(
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
                    msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in TurnoRepository deleteAll`() =
        runTest {
            val list = listOf(
                Turno(
                    idTurno = 1,
                    codTurno = 1,
                    nroTurno = 1,
                    descrTurno = "descrTurno"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                turnoRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                turnoRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableTurno(
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
                    msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_turno",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in TurnoRepository addAll`() =
        runTest {
            val list = listOf(
                Turno(
                    idTurno = 1,
                    codTurno = 1,
                    nroTurno = 1,
                    descrTurno = "descrTurno"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                turnoRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                turnoRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                turnoRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableTurno(
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
                    msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_turno",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_turno",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableTurno -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}