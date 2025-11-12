package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.entities.variable.NoteMotoMec
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.STOP
import br.com.usinasantafe.cmm.utils.WORK
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class IHistoryListTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val activityRepository = mock<ActivityRepository>()
    private val stopRepository = mock<StopRepository>()
    private val usecase = IListHistory(
        motoMecRepository = motoMecRepository,
        activityRepository = activityRepository,
        stopRepository = stopRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository noteList`() =
        runTest {
            whenever(
                motoMecRepository.noteList()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.noteList",
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
                "IHistoryList -> IMotoMecRepository.noteList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in StopRepository getById`() =
        runTest {
            val list = listOf(
                NoteMotoMec(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = 1
                ),
                NoteMotoMec(
                    id = 2,
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            whenever(
                motoMecRepository.noteList()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                stopRepository.getById(1)
            ).thenReturn(
                resultFailure(
                    "IStopRepository.getById",
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
                "IHistoryList -> IStopRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ActivityRepository getById`() =
        runTest {
            val list = listOf(
                NoteMotoMec(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = 1
                ),
                NoteMotoMec(
                    id = 2,
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            whenever(
                motoMecRepository.noteList()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                stopRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Stop(
                        idStop = 1,
                        codStop = 20,
                        descrStop = "CHECKLIST"
                    )
                )
            )
            whenever(
                activityRepository.getById(1)
            ).thenReturn(
                resultFailure(
                    "IActivityRepository.getById",
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
                "IHistoryList -> IActivityRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val list = listOf(
                NoteMotoMec(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = 1,
                    dateHour = Date(1761046154000)
                ),
                NoteMotoMec(
                    id = 2,
                    nroOS = 123456,
                    idActivity = 1,
                    dateHour = Date(1761132554000)
                )
            )
            whenever(
                motoMecRepository.noteList()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                stopRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Stop(
                        idStop = 1,
                        codStop = 20,
                        descrStop = "CHECKLIST"
                    )
                )
            )
            whenever(
                activityRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Activity(
                        idActivity = 1,
                        codActivity = 1,
                        descrActivity = "CARREGAMENTO DE CANA"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val resultList = result.getOrNull()!!
            assertEquals(
                resultList.size,
                2
            )
            val model1 = resultList[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.function.first,
                2
            )
            assertEquals(
                model1.function.second,
                STOP
            )
            assertEquals(
                model1.descr,
                "CHECKLIST"
            )
            assertEquals(
                model1.dateHour,
                "21/10/2025 08:29"
            )
            val model2 = resultList[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model1.function.first,
                1
            )
            assertEquals(
                model1.function.second,
                WORK
            )
            assertEquals(
                model2.descr,
                "CARREGAMENTO DE CANA"
            )
            assertEquals(
                model2.dateHour,
                "22/10/2025 08:29"
            )
        }

}