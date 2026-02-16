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
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNoteTest {

    private val rItemMenuStopRepository = mock<RItemMenuStopRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val saveNote = mock<SaveNote>()

    private val usecase = ISetNote(
        rItemMenuStopRepository = rItemMenuStopRepository,
        motoMecRepository = motoMecRepository,
        saveNote = saveNote
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
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(1)
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
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(1)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
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
    fun `Check return failure if have error in SaveNote`() =
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
                saveNote(1, 1, 1)
            ).thenReturn(
                resultFailure(
                    "SaveNote",
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
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(1)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            verify(motoMecRepository, atLeastOnce()).setIdStop(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> SaveNote"
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
                saveNote(1, 1, 1)
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
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(1)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            verify(motoMecRepository, atLeastOnce()).setIdStop(1)
            verify(saveNote, atLeastOnce()).invoke(1, 1, 1)
        }

    @Test
    fun `Check return correct if function execute successfully and idStop is null`() =
        runTest {
            whenever(
                rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                    idFunction = 1,
                    idApp = 2
                )
            ).thenReturn(
                Result.success(null)
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
                saveNote(1, 1, 1)
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
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(1)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            verify(motoMecRepository, never()).setIdStop(1)
            verify(saveNote, atLeastOnce()).invoke(1, 1, 1)
        }

}