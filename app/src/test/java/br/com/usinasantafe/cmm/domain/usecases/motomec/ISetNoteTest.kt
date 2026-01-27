package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNoteTest {

    private val rItemMenuStopRepository = mock<RItemMenuStopRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()

    private val usecase = ISetNote(
        rItemMenuStopRepository = rItemMenuStopRepository,
        motoMecRepository = motoMecRepository,
        functionActivityRepository = functionActivityRepository
    )

    @Test
    fun `Check return failure if have error in RItemMenuStopRepository getIdStopByIdFunctionAndIdApp`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                resultFailure(
                    "IRItemMenuStopRepository.getIdStopByIdFunctionAndIdApp",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IRItemMenuStopRepository.getIdStopByIdFunctionAndIdApp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getNroOSHeader`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNroOSHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.getNroOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdActivityHeader`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdActivityHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.getIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setNroOSNote`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setNroOSNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.setNroOSNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return failure if have error in MotoMecRepository setIdActivityNote`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdActivityNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.setIdActivityNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setIdStop`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdStop",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.setIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository saveNote`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository hasByIdAndType`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.hasByIdAndType",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IFunctionActivityRepository.hasByIdAndType"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository insertInitialPerformance`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.insertInitialPerformance()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.insertInitialPerformance",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.insertInitialPerformance"
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
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.insertInitialPerformance()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

}