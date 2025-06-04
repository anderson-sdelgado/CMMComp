package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetDescrEquipTest {

    private val equipRepository = mock<EquipRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetDescrEquip(
        configRepository = configRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                resultFailure(
                    "ConfigRepository.get",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetDescrEquip -> ConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return failure if have error in EquipRepository getDescr`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        idEquip = 1,
                    )
                )
            )
            whenever(
                equipRepository.getDescrByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRepository.getDescr",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetDescrEquip -> EquipRepository.getDescr"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        idEquip = 1,
                    )
                )
            )
            whenever(
                equipRepository.getDescrByIdEquip(1)
            ).thenReturn(
                Result.success("200 - TRATOR")
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "200 - TRATOR"
            )
        }

}