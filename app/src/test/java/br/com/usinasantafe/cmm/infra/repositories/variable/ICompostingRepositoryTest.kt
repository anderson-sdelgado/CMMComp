package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CompoundCompostingRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.InputCompostingRoomDatasource
import br.com.usinasantafe.cmm.lib.FlowComposting
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICompostingRepositoryTest {

    private val inputCompostingRoomDatasource = mock<InputCompostingRoomDatasource>()
    private val compoundCompostingRoomDatasource = mock<CompoundCompostingRoomDatasource>()
    private val repository = ICompostingRepository(
        inputCompostingRoomDatasource = inputCompostingRoomDatasource,
        compoundCompostingRoomDatasource = compoundCompostingRoomDatasource
    )

    @Test
    fun `hasCompostingInputLoadSent - Check return failure if have error in CompostingInputRoomDatasource hasSentLoad`() =
        runTest {
            whenever(
                inputCompostingRoomDatasource.hasSentLoad()
            ).thenReturn(
                resultFailure(
                    "IInputCompostingRoomDatasource.hasSentLoad",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasCompostingInputLoadSent()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICompostingRepository.hasCompostingInputLoadSent -> IInputCompostingRoomDatasource.hasSentLoad"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasCompostingInputLoadSent - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                inputCompostingRoomDatasource.hasSentLoad()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasCompostingInputLoadSent()
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
    fun `hasWill - FlowComposting INPUT - Check return failure if have error in InputCompostingRoomDatasource hasWill`() =
        runTest {
            whenever(
                inputCompostingRoomDatasource.hasWill()
            ).thenReturn(
                resultFailure(
                    "IInputCompostingDatasource.hasWill",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasWill(FlowComposting.INPUT)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICompostingRepository.hasWill -> IInputCompostingDatasource.hasWill"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasWill - FlowComposting INPUT - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                inputCompostingRoomDatasource.hasWill()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasWill(FlowComposting.INPUT)
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
    fun `hasWill - Check return failure if have error in CompoundCompostingRoomDatasource hasWill`() =
        runTest {
            whenever(
                compoundCompostingRoomDatasource.hasWill()
            ).thenReturn(
                resultFailure(
                    "ICompoundCompostingRoomDatasource.hasWill",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasWill(FlowComposting.COMPOUND)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICompostingRepository.hasWill -> ICompoundCompostingRoomDatasource.hasWill"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasWill - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                compoundCompostingRoomDatasource.hasWill()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasWill(FlowComposting.COMPOUND)
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