package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.MotoMec
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TypeOper
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableMotoMecTest {

    private val getToken = mock<GetToken>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val updateTableMotoMec = IUpdateTableMotoMec(
        getToken = getToken,
        motoMecRepository = motoMecRepository
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
            val result = updateTableMotoMec(
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
                    msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                motoMecRepository.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableMotoMec(
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
                    msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository deleteAll`() =
        runTest {
            val list = listOf(
                MotoMec(
                    idMotoMec = 1,
                    idOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.PARADA,
                    aplicOperMotoMec = 1,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    codFuncaoOperMotoMec = 1,
                    descrOperMotoMec = "descr"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                motoMecRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                motoMecRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableMotoMec(
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
                    msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_motomec",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository addAll`() =
        runTest {
            val list = listOf(
                MotoMec(
                    idMotoMec = 1,
                    idOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.PARADA,
                    aplicOperMotoMec = 1,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    codFuncaoOperMotoMec = 1,
                    descrOperMotoMec = "descr"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                motoMecRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                motoMecRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableMotoMec(
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
                    msgProgress = "Recuperando dados da tabela tb_motomec do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_motomec",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_motomec",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableMotoMec -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}