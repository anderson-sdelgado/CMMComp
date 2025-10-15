package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IGetItemCheckListTest {

    private val itemCheckListRepository = mock<ItemCheckListRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val turnRepository = mock<TurnRepository>()
    private val usecase = IGetItemCheckList(
        itemCheckListRepository = itemCheckListRepository,
        checkListRepository = checkListRepository,
        configRepository = configRepository,
        equipRepository = equipRepository,
        motoMecRepository = motoMecRepository,
        turnRepository = turnRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getNroEquip and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IConfigRepository.getNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getRegOperator and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getRegOperator",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IMotoMecRepository.getRegOperator"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdTurnHeader and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdTurnHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IMotoMecRepository.getIdTurnHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in TurnRepository geNroTurnByIdTurn and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                turnRepository.getNroTurnByIdTurn(1)
            ).thenReturn(
                resultFailure(
                    "ITurnRepository.geNroTurnByIdTurn",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> ITurnRepository.geNroTurnByIdTurn"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository saveHeader and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                turnRepository.getNroTurnByIdTurn(1)
            ).thenReturn(
                Result.success(2)
            )
            val modelCaptor = argumentCaptor<HeaderCheckList>().apply {
                whenever(
                    checkListRepository.saveHeader(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "ICheckListRepository.saveHeader",
                        "-",
                        Exception()
                    )
                )
            }
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> ICheckListRepository.saveHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroEquip,
                2200L
            )
            assertEquals(
                model.regOperator,
                19759
            )
            assertEquals(
                model.nroTurn,
                2
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getIdEquip and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                turnRepository.getNroTurnByIdTurn(1)
            ).thenReturn(
                Result.success(2)
            )
            val modelCaptor = argumentCaptor<HeaderCheckList>().apply {
                whenever(
                    checkListRepository.saveHeader(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IConfigRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroEquip,
                2200L
            )
            assertEquals(
                model.regOperator,
                19759
            )
            assertEquals(
                model.nroTurn,
                2
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdCheckListByIdEquip and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                turnRepository.getNroTurnByIdTurn(1)
            ).thenReturn(
                Result.success(2)
            )
            val modelCaptor = argumentCaptor<HeaderCheckList>().apply {
                whenever(
                    checkListRepository.saveHeader(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdCheckListByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IEquipRepository.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroEquip,
                2200L
            )
            assertEquals(
                model.regOperator,
                19759
            )
            assertEquals(
                model.nroTurn,
                2
            )
        }

    @Test
    fun `Check return failure if have error in ItemCheckListRepository listByIdCheckList and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                turnRepository.getNroTurnByIdTurn(1)
            ).thenReturn(
                Result.success(2)
            )
            val modelCaptor = argumentCaptor<HeaderCheckList>().apply {
                whenever(
                    checkListRepository.saveHeader(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                itemCheckListRepository.listByIdCheckList(20)
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRepository.listByIdCheckList",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IItemCheckListRepository.listByIdCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroEquip,
                2200L
            )
            assertEquals(
                model.regOperator,
                19759
            )
            assertEquals(
                model.nroTurn,
                2
            )
        }

    @Test
    fun `Check return data correct if process execute successfully and pos is 1`() =
        runTest {
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(2200L)
            )
            whenever(
                motoMecRepository.getRegOperatorHeader()
            ).thenReturn(
                Result.success(19759)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                turnRepository.getNroTurnByIdTurn(1)
            ).thenReturn(
                Result.success(2)
            )
            val modelCaptor = argumentCaptor<HeaderCheckList>().apply {
                whenever(
                    checkListRepository.saveHeader(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                itemCheckListRepository.listByIdCheckList(20)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemCheckList(
                            idItemCheckList = 1,
                            idCheckList = 20,
                            descrItemCheckList = "Item 1"
                        )
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.descr,
                "Item 1"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroEquip,
                2200L
            )
            assertEquals(
                model.regOperator,
                19759
            )
            assertEquals(
                model.nroTurn,
                2
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getIdEquip and pos is greater than 1`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IConfigRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdCheckListByIdEquip and pos is greater than 1`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdCheckListByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IEquipRepository.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemCheckListRepository listByIdCheckList and pos is greater than 1`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                itemCheckListRepository.listByIdCheckList(20)
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRepository.listByIdCheckList",
                    "-",
                    Exception()
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IItemCheckListRepository.listByIdCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return data correct if process execute successfully and pos is greater than 1`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                itemCheckListRepository.listByIdCheckList(20)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemCheckList(
                            idItemCheckList = 1,
                            idCheckList = 20,
                            descrItemCheckList = "Item 1"
                        ),
                        ItemCheckList(
                            idItemCheckList = 2,
                            idCheckList = 20,
                            descrItemCheckList = "Item 2"
                        )
                    )
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.id,
                2
            )
            assertEquals(
                entity.descr,
                "Item 2"
            )
        }
}