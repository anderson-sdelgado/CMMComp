package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StatusTranshipment
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class IGetStatusTranshipmentTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = IGetStatusTranshipment(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IGetStatusTranshipment -> IMotoMecRepository.getIdByHeaderOpen",
                result.exceptionOrNull()!!.message!!
            )
            assertEquals(
                "java.lang.Exception",
                result.exceptionOrNull()!!.cause.toString()
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository hasNoteByIdHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.hasNoteByIdHeader",
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
                "IGetStatusTranshipment -> IMotoMecRepository.hasNoteByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return StatusTranshipment WITHOUT_NOTE if function execute successfully and return false`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.WITHOUT_NOTE
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getNoteLastByIdHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNoteLastByIdHeader",
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
                "IGetStatusTranshipment -> IMotoMecRepository.getNoteLastByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return  StatusTranshipment WITHOUT_NOTE if function execute successfully and last Note is type WORK`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    ItemMotoMec(
                        id = 1,
                        nroOS = 123456,
                        idActivity = 1
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.WITHOUT_NOTE
            )
        }

    @Test
    fun `Check return  StatusTranshipment TIME_INVALID if function execute successfully and idEquipTrans is not null and last dateHour note minor than dateHour now minus 10 minutes`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    ItemMotoMec(
                        id = 1,
                        nroOS = 123456,
                        idActivity = 1,
                        idStop = 1,
                        nroEquipTranshipment = 1,
                        dateHour = Date()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.TIME_INVALID
            )
        }

    @Test
    fun `Check return  StatusTranshipment OK if function execute successfully and idEquipTrans is null and last dateHour note minor than dateHour now minus 10 minutes`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    ItemMotoMec(
                        id = 1,
                        nroOS = 123456,
                        idActivity = 1,
                        idStop = 1,
                        dateHour = Date()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.OK
            )
        }

    @Test
    fun `Check return  StatusTranshipment OK if function execute successfully and idEquipTrans is not null and last dateHour note greater than dateHour now minus 10 minutes`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    ItemMotoMec(
                        id = 1,
                        nroOS = 123456,
                        idActivity = 1,
                        idStop = 1,
                        nroEquipTranshipment = 1,
                        dateHour = Date(1763476202000)
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.OK
            )
        }


}

