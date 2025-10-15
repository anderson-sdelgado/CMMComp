package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.LeiraRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import org.mockito.Mockito.mock

class IUpdateTableLeiraTest {

    private val getToken = mock<GetToken>()
    private val leiraRepository = mock<LeiraRepository>()
    private val updateTableLeira = IUpdateTableLeira(
        getToken = getToken,
        leiraRepository = leiraRepository
    )
//
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
//            val result = updateTableLeira(
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
//                    msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in LeiraRepository recoverAll`() =
//        runTest {
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                leiraRepository.recoverAll("token")
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableLeira(
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
//                    msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in LeiraRepository deleteAll`() =
//        runTest {
//            val list = listOf(
//                Leira(
//                    idLeira = 1,
//                    codLeira = 1,
//                    statusLeira = StatusLeira.LIBERADA
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                leiraRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                leiraRepository.deleteAll()
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableLeira(
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
//                    msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_leira",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in LeiraRepository addAll`() =
//        runTest {
//            val list = listOf(
//                Leira(
//                    idLeira = 1,
//                    codLeira = 1,
//                    statusLeira = StatusLeira.LIBERADA
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                leiraRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                leiraRepository.deleteAll()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                leiraRepository.addAll(list)
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableLeira(
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
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_leira do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_leira",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_leira",
//                    currentProgress = updatePercentage(3f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[3],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableLeira -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }

}