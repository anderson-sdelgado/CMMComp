package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
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

class IUpdateTableEquipByIdEquipTest {

    private val getToken = mock<GetToken>()
    private val equipRepository = mock<EquipRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val updateTableEquip = IUpdateTableEquipByIdEquip(
        getToken = getToken,
        equipRepository = equipRepository,
        configRepository = configRepository
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
            val result = updateTableEquip(
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableEquip(
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getListByIdEquip`() =
        runTest {
            val config = Config(
                idEquip = 10
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            whenever(
                equipRepository.getListByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableEquip(
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository deleteAll`() =
        runTest {
            val config = Config(
                idEquip = 10
            )
            val list = listOf(
                Equip(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measure = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            whenever(
                equipRepository.getListByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                equipRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableEquip(
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository addAll`() =
        runTest {
            val config = Config(
                idEquip = 10,
                idBD = 1
            )
            val list = listOf(
                Equip(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measure = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            whenever(
                equipRepository.getListByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                equipRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableEquip(
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableEquip -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}