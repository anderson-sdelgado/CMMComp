package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.REquipAtiv
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipAtivRepository
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

class IUpdateTableREquipAtivTest {

    private val getToken = mock<GetToken>()
    private val rEquipAtivRepository = mock<REquipAtivRepository>()
    private val updateTableREquipAtiv = IUpdateTableREquipAtiv(
        getToken = getToken,
        rEquipAtivRepository = rEquipAtivRepository
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
            val result = updateTableREquipAtiv(
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
                    msgProgress = "Recuperando dados da tabela tb_r_equip_ativ do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in REquipAtivRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rEquipAtivRepository.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableREquipAtiv(
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
                    msgProgress = "Recuperando dados da tabela tb_r_equip_ativ do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in REquipAtivRepository deleteAll`() =
        runTest {
            val list = listOf(
                REquipAtiv(
                    idREquipAtiv = 1,
                    idAtiv = 1,
                    idEquip = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rEquipAtivRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rEquipAtivRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableREquipAtiv(
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
                    msgProgress = "Recuperando dados da tabela tb_r_equip_ativ do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_equip_ativ",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in REquipAtivRepository addAll`() =
        runTest {
            val list = listOf(
                REquipAtiv(
                    idREquipAtiv = 1,
                    idAtiv = 1,
                    idEquip = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rEquipAtivRepository.recoverAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                rEquipAtivRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rEquipAtivRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            val result = updateTableREquipAtiv(
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
                    msgProgress = "Recuperando dados da tabela tb_r_equip_ativ do Web Service",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_equip_ativ",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_equip_ativ",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    msgProgress = "UpdateTableREquipAtiv -> Error -> Exception -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}