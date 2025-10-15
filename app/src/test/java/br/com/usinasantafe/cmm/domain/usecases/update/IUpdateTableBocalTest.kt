package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.BocalRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import org.mockito.Mockito.mock

class IUpdateTableBocalTest {

    private val getToken = mock<GetToken>()
    private val bocalRepository = mock<BocalRepository>()
    private val updateTableBocal = IUpdateTableBocal(
        getToken = getToken,
        bocalRepository = bocalRepository
    )

//    @Test
//    fun `Check return failure if have error in GetToken`() =
//        runTest {
//            whenever(
//                getToken()
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableBocal(
//                sizeAll = 7f,
//                count = 1f
//            )
//            val list = result.toList()
//            assertEquals(
//                result.count(),
//                2
//            )
//            assertEquals(
//                list[0],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in BocalRepository recoverAll`() =
//        runTest {
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                bocalRepository.recoverAll("token")
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableBocal(
//                sizeAll = 7f,
//                count = 1f
//            )
//            val list = result.toList()
//            assertEquals(
//                result.count(),
//                2
//            )
//            assertEquals(
//                list[0],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in BocalRepository deleteAll`() =
//        runTest {
//            val list = listOf(
//                Bocal(
//                    idBocal = 1,
//                    codBocal = 1,
//                    descrBocal = "descrBocal"
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                bocalRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                bocalRepository.deleteAll()
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableBocal(
//                sizeAll = 7f,
//                count = 1f
//            )
//            val resultList = result.toList()
//            assertEquals(
//                result.count(),
//                3
//            )
//            assertEquals(
//                resultList[0],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_bocal",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in BocalRepository addAll`() =
//        runTest {
//            val list = listOf(
//                Bocal(
//                    idBocal = 1,
//                    codBocal = 1,
//                    descrBocal = "descrBocal"
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                bocalRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                bocalRepository.deleteAll()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                bocalRepository.addAll(list)
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableBocal(
//                sizeAll = 7f,
//                count = 1f
//            )
//            val resultList = result.toList()
//            assertEquals(
//                result.count(),
//                4
//            )
//            assertEquals(
//                resultList[0],
//                ResultUpdate(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_bocal do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdate(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_bocal",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdate(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_bocal",
//                    currentProgress = updatePercentage(3f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[3],
//                ResultUpdate(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableBocal -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }

}