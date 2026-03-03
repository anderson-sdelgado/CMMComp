package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetSeqItemTest {

    private val mechanicRepository = mock<MechanicRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetSeqItem(
        mechanicRepository = mechanicRepository,
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if have error in MechanicRepository setSeqItem`() =
        runTest {
            whenever(
                mechanicRepository.setSeqItem(1)
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.setSeqItem",
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
                "ISetSeqItem -> IMechanicRepository.setSeqItem"
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
                motoMecRepository.getIdHeaderPointing()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            verify(mechanicRepository, atLeastOnce()).setSeqItem(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSeqItem -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MechanicRepository save`() =
        runTest {
            whenever(
                motoMecRepository.getIdHeaderPointing()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.save(1)
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.save",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            verify(mechanicRepository, atLeastOnce()).setSeqItem(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSeqItem -> IMechanicRepository.save"
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
                motoMecRepository.getIdHeaderPointing()
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase(1)
            verify(mechanicRepository, atLeastOnce()).setSeqItem(1)
            verify(mechanicRepository, atLeastOnce()).save(1)
            assertEquals(
                result.isSuccess,
                true
            )
        }

}