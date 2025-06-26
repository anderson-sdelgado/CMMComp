package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdEquipTest {

    private val configRepository = mock<ConfigRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = ISetIdEquip(
        motoMecRepository = motoMecRepository,
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
                "ISetIdEquip -> ConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getTypeByIdEquip`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        idEquip = 10
                    )
                )
            )
            whenever(
                equipRepository.getTypeFertByIdEquip(
                    idEquip = 10
                )
            ).thenReturn(
                resultFailure(
                    "EquipRepository.getTypeByIdEquip",
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
                "ISetIdEquip -> EquipRepository.getTypeByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdEquip`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        idEquip = 10
                    )
                )
            )
            whenever(
                equipRepository.getTypeFertByIdEquip(
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL
                )
            ).thenReturn(
                resultFailure(
                    "HeaderMotoMecRepository.setIdEquip",
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
                "ISetIdEquip -> HeaderMotoMecRepository.setIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and typeEquip is 1`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        idEquip = 10,
                    )
                )
            )
            whenever(
                equipRepository.getTypeFertByIdEquip(
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return correct if function execute successfully and typeEquip is 3`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        idEquip = 10,
                    )
                )
            )
            whenever(
                equipRepository.getTypeFertByIdEquip(
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(3)
            )
            whenever(
                motoMecRepository.setDataEquipHeader(
                    idEquip = 10,
                    typeEquip = TypeEquip.FERT
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }
}