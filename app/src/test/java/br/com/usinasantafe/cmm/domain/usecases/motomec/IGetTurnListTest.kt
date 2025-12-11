package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetTurnListTest {

    private val turnRepository = mock<TurnRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IListTurn(
        turnRepository = turnRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getCodTurnEquipByIdEquip`() =
        runTest {
            whenever(
                equipRepository.getCodTurnEquip()
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
                "IListTurn -> EquipRepository.getCodTurnEquipByIdEquip"
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
                equipRepository.getCodTurnEquip()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                turnRepository.listByCodTurnEquip(
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
                "IListTurn -> TurnRepository.getListByCodTurnEquip"
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
                equipRepository.getCodTurnEquip()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                turnRepository.listByCodTurnEquip(
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