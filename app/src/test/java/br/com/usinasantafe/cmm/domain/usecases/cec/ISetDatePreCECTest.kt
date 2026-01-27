package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.EXIT_FIELD
import br.com.usinasantafe.cmm.lib.EXIT_MILL
import br.com.usinasantafe.cmm.lib.FIELD_ARRIVAL
import br.com.usinasantafe.cmm.lib.StatusPreCEC
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.Result
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetDatePreCECTest {

    private val cecRepository = mock<CECRepository>()
    private val usecase = ISetDatePreCEC(
        cecRepository = cecRepository
    )

    @Test
    fun `Check return failure if have error in CECRepository get`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                resultFailure(
                    "ICECRepository.get",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDatePreCEC -> ICECRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return StatusPreCEC EXIT_MILL if dateMillExit is null and type is FIELD_ARRIVAL`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
        }

    @Test
    fun `Check return StatusPreCEC EXIT_MILL if dateMillExit is null and type is EXIT_FIELD`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
        }

    @Test
    fun `Check return StatusPreCEC FIELD_ARRIVAL if dateFieldArrival is null and type is EXIT_MILL`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date()
                    )
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
        }

    @Test
    fun `Check return StatusPreCEC FIELD_ARRIVAL if dateFieldArrival is null and type is EXIT_FIELD`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date()
                    )
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
        }

    @Test
    fun `Check return StatusPreCEC EXIT_FIELD if dateFieldExit is null and type is EXIT_MILL`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date(),
                        dateFieldArrival = Date()
                    )
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_FIELD
            )
        }

    @Test
    fun `Check return StatusPreCEC EXIT_FIELD if dateFieldExit is null and type is FIELD_ARRIVAL`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date(),
                        dateFieldArrival = Date()
                    )
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_FIELD
            )
        }

    @Test
    fun `Check return failure if have error in CECRepository setDateExitMill`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC()
                )
            )
            whenever(
                cecRepository.setDateExitMill(any())
            ).thenReturn(
                resultFailure(
                    "ICECRepository.setDateExitMill",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDatePreCEC -> ICECRepository.setDateExitMill"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CECRepository setDateFieldArrival`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date()
                    )
                )
            )
            whenever(
                cecRepository.setDateFieldArrival(any())
            ).thenReturn(
                resultFailure(
                    "ICECRepository.setDateFieldArrival",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDatePreCEC -> ICECRepository.setDateFieldArrival"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CECRepository setDateExitArrival`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date(),
                        dateFieldArrival = Date()
                    )
                )
            )
            whenever(
                cecRepository.setDateExitField(any())
            ).thenReturn(
                resultFailure(
                    "ICECRepository.setDateExitArrival",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDatePreCEC -> ICECRepository.setDateExitArrival"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return StatusPreCEC EXIT_MILL if CECRepository setDateExitMill execute successfully`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC()
                )
            )
            whenever(
                cecRepository.setDateExitMill(any())
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
        }

    @Test
    fun `Check return StatusPreCEC FIELD_ARRIVAL if CECRepository setDateFieldArrival execute successfully`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date()
                    )
                )
            )
            whenever(
                cecRepository.setDateFieldArrival(any())
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
        }

    @Test
    fun `Check return StatusPreCEC EXIT_FIELD if CECRepository setDateExitField execute successfully`() =
        runTest {
            whenever(
                cecRepository.get()
            ).thenReturn(
                Result.success(
                    PreCEC(
                        dateExitMill = Date(),
                        dateFieldArrival = Date()
                    )
                )
            )
            whenever(
                cecRepository.setDateExitField(any())
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_FIELD
            )
        }
}