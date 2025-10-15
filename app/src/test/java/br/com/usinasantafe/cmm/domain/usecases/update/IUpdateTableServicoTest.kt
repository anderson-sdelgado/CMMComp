package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.ServicoRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import org.mockito.Mockito.mock

class IUpdateTableServicoTest {

    private val getToken = mock<GetToken>()
    private val servicoRepository = mock<ServicoRepository>()
    private val updateTableServico = IUpdateTableServico(
        getToken = getToken,
        servicoRepository = servicoRepository
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
//            val result = updateTableServico(
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
//                    msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in ServicoRepository recoverAll`() =
//        runTest {
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                servicoRepository.recoverAll("token")
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableServico(
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
//                    msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in ServicoRepository deleteAll`() =
//        runTest {
//            val list = listOf(
//                Servico(
//                    idServico = 1,
//                    codServico = 1,
//                    descrServico = "descrServico"
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                servicoRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                servicoRepository.deleteAll()
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableServico(
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
//                    msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_servico",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in ServicoRepository addAll`() =
//        runTest {
//            val list = listOf(
//                Servico(
//                    idServico = 1,
//                    codServico = 1,
//                    descrServico = "descrServico"
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                servicoRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                servicoRepository.deleteAll()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                servicoRepository.addAll(list)
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableServico(
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
//                    msgProgress = "Recuperando dados da tabela tb_servico do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_servico",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_servico",
//                    currentProgress = updatePercentage(3f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[3],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableServico -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }

}