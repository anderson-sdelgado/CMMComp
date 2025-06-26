package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.IListTurn
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetTurnListTest {

    private val configRepository = mock<ConfigRepository>()
    private val turnRepository = mock<TurnRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IListTurn(
        configRepository = configRepository,
        turnRepository = turnRepository,
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
                "IGetTurnList -> ConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getCodTurnEquipByIdEquip`() =
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
                equipRepository.getCodTurnEquipByIdEquip(
                    idEquip = 1
                )
            ).thenReturn(
                resultFailure(
                    "EquipRepository.getCodTurnEquipByIdEquip",
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
                "IGetTurnList -> EquipRepository.getCodTurnEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in TurnRepository getListByCodTurnEquip`() =
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
                equipRepository.getCodTurnEquipByIdEquip(
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                turnRepository.getListByCodTurnEquip(
                    codTurnEquip = 10
                )
            ).thenReturn(
                resultFailure(
                    "TurnRepository.getListByCodTurnEquip",
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
                "IGetTurnList -> TurnRepository.getListByCodTurnEquip"
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
                equipRepository.getCodTurnEquipByIdEquip(
                    idEquip = 1
                )
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                turnRepository.getListByCodTurnEquip(
                    codTurnEquip = 10
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Turn(
                            idTurn = 1,
                            codTurnEquip = 10,
                            nroTurn = 2,
                            descrTurn = "TURNO 1"
                        )
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idTurn,
                1
            )
            assertEquals(
                entity.codTurnEquip,
                10
            )
            assertEquals(
                entity.nroTurn,
                2
            )
            assertEquals(
                entity.descrTurn,
                "TURNO 1"
            )
        }

}