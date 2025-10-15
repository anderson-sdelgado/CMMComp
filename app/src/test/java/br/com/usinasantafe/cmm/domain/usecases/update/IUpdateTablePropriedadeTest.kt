package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.PropriedadeRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import org.mockito.Mockito.mock

class IUpdateTablePropriedadeTest {

    private val getToken = mock<GetToken>()
    private val propriedadeRepository = mock<PropriedadeRepository>()
    private val updateTablePropriedade = IUpdateTablePropriedade(
        getToken = getToken,
        propriedadeRepository = propriedadeRepository
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
//            val result = updateTablePropriedade(
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
//                    msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in PropriedadeRepository recoverAll`() =
//        runTest {
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                propriedadeRepository.recoverAll("token")
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTablePropriedade(
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
//                    msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in PropriedadeRepository deleteAll`() =
//        runTest {
//            val list = listOf(
//                Propriedade(
//                    idPropriedade = 1,
//                    codPropriedade = 1,
//                    descrPropriedade = "descrPropriedade"
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                propriedadeRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                propriedadeRepository.deleteAll()
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTablePropriedade(
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
//                    msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_propriedade",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in PropriedadeRepository addAll`() =
//        runTest {
//            val list = listOf(
//                Propriedade(
//                    idPropriedade = 1,
//                    codPropriedade = 1,
//                    descrPropriedade = "descrPropriedade"
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                propriedadeRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                propriedadeRepository.deleteAll()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                propriedadeRepository.addAll(list)
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTablePropriedade(
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
//                    msgProgress = "Recuperando dados da tabela tb_propriedade do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_propriedade",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_propriedade",
//                    currentProgress = updatePercentage(3f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[3],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTablePropriedade -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }

}