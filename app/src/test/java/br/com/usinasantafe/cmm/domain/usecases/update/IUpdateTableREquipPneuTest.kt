package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.REquipPneuRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import org.mockito.Mockito.mock

class IUpdateTableREquipPneuTest {

    private val getToken = mock<GetToken>()
    private val rEquipPneuRepository = mock<REquipPneuRepository>()
    private val updateTableREquipPneu = IUpdateTableREquipPneu(
        getToken = getToken,
        rEquipPneuRepository = rEquipPneuRepository
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
//            val result = updateTableREquipPneu(
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
//                    msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in REquipPneuRepository recoverAll`() =
//        runTest {
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                rEquipPneuRepository.recoverAll("token")
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableREquipPneu(
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
//                    msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                list[1],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in REquipPneuRepository deleteAll`() =
//        runTest {
//            val list = listOf(
//                REquipPneu(
//                    idREquipPneu = 1,
//                    idEquip = 1,
//                    idPosConfPneu = 1,
//                    statusPneu = 1,
//                    posPneu = 1
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                rEquipPneuRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                rEquipPneuRepository.deleteAll()
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableREquipPneu(
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
//                    msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_r_equip_pneu",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in REquipPneuRepository addAll`() =
//        runTest {
//            val list = listOf(
//                REquipPneu(
//                    idREquipPneu = 1,
//                    idEquip = 1,
//                    idPosConfPneu = 1,
//                    statusPneu = 1,
//                    posPneu = 1
//                )
//            )
//            whenever(
//                getToken()
//            ).thenReturn(
//                Result.success("token")
//            )
//            whenever(
//                rEquipPneuRepository.recoverAll("token")
//            ).thenReturn(
//                Result.success(
//                    list
//                )
//            )
//            whenever(
//                rEquipPneuRepository.deleteAll()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                rEquipPneuRepository.addAll(list)
//            ).thenReturn(
//                resultFailure(
//                    "Error",
//                    "Exception",
//                    Exception()
//                )
//            )
//            val result = updateTableREquipPneu(
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
//                    msgProgress = "Recuperando dados da tabela tb_r_equip_pneu do Web Service",
//                    currentProgress = updatePercentage(1f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[1],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_r_equip_pneu",
//                    currentProgress = updatePercentage(2f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[2],
//                ResultUpdateModel(
//                    flagProgress = true,
//                    msgProgress = "Salvando dados na tabela tb_r_equip_pneu",
//                    currentProgress = updatePercentage(3f, 1f, 7f)
//                )
//            )
//            assertEquals(
//                resultList[3],
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    msgProgress = "UpdateTableREquipPneu -> Error -> Exception -> java.lang.Exception",
//                    currentProgress = 1f,
//                )
//            )
//        }

}