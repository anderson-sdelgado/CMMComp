package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class IListStopTest {

    private val motoMecRepository = Mockito.mock<MotoMecRepository>()
    private val rActivityStopRepository = Mockito.mock<RActivityStopRepository>()
    private val stopRepository = Mockito.mock<StopRepository>()
    private val usecase = IListStop(
        motoMecRepository = motoMecRepository,
        rActivityStopRepository = rActivityStopRepository,
        stopRepository = stopRepository
    )

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository getIdActivity`() =
        runTest {
            whenever(
                motoMecRepository.getIdActivity()
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecRepository.getIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IListStop -> INoteMotoMecRepository.getIdActivity"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in RActivityStopRepository listByIdActivity`() =
        runTest {
            whenever(
                motoMecRepository.getIdActivity()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                rActivityStopRepository.listByIdActivity(1)
            ).thenReturn(
                resultFailure(
                    context = "IRActivityStopRepository.listByIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IListStop -> IRActivityStopRepository.listByIdActivity"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in StopRepository listByIdList`() =
        runTest {
            val listRActivityStop = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 1,
                    idStop = 1
                )
            )
            val listIdStop = listOf(1)
            whenever(
                motoMecRepository.getIdActivity()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                rActivityStopRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(listRActivityStop)
            )
            whenever(
                stopRepository.listByIdList(listIdStop)
            ).thenReturn(
                resultFailure(
                    context = "IStopRepository.listByIdList",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IListStop -> IStopRepository.listByIdList"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val listRActivityStop = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 1,
                    idStop = 1
                )
            )
            val listIdStop = listOf(1)
            val listStop = listOf(
                Stop(
                    idStop = 1,
                    codStop = 10,
                    descrStop = "PARADA"
                )
            )
            whenever(
                motoMecRepository.getIdActivity()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                rActivityStopRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(listRActivityStop)
            )
            whenever(
                stopRepository.listByIdList(listIdStop)
            ).thenReturn(
                Result.success(listStop)
            )
            val result = usecase()
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            Assert.assertEquals(
                list.size,
                1
            )
            val entity = list.first()
            Assert.assertEquals(
                entity.id,
                1
            )
            Assert.assertEquals(
                entity.descr,
                "10 - PARADA"
            )
        }


}