package br.com.usinasantafe.cmm.domain.usecases.background

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.WorkManagerTestInitHelper
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
@ExperimentalCoroutinesApi
class ProcessWorkManagerTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var context: Context

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setup() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

    }

    @Test
    fun return_success_if_status_not_is_send() = runTest {

        val request = OneTimeWorkRequestBuilder<ProcessWorkManager>().build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(request).result.get()

        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.SUCCEEDED, workInfo!!.state)
    }

    @Test
    fun return_success_if_status_is_send_and_not_have_header_send() = runTest {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                app = "CMM",
                idServ = 1,
                nroEquip = 2200,
                number = 16997417840,
                version = "1.0",
                password = "12345",
                idEquip = 1,
                statusSend = StatusSend.SEND
            )
        )

        val request = OneTimeWorkRequestBuilder<ProcessWorkManager>().build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(request).result.get()

        var workInfo: WorkInfo?
        val maxWait = 10_000L
        val start = System.currentTimeMillis()

        do {
            workInfo = workManager.getWorkInfoById(request.id).get()
            if (workInfo?.state?.isFinished == true) break
            Thread.sleep(100)
        } while (System.currentTimeMillis() - start < maxWait)

        assertEquals(WorkInfo.State.SUCCEEDED, workInfo!!.state)
    }

    @Test
    fun return_retry_if_status_is_send_and_have_header_send() = runTest {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                app = "CMM",
                idServ = 1,
                nroEquip = 2200,
                number = 16997417840,
                version = "1.0",
                password = "12345",
                idEquip = 1,
                statusSend = StatusSend.SEND
            )
        )

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 1,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1.0,
                dateHourInitial = Date(),
                status = Status.OPEN,
                statusSend = StatusSend.SEND,
                statusCon = true
            )
        )

        val request = OneTimeWorkRequestBuilder<ProcessWorkManager>().build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(request).result.get()

        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.RUNNING, workInfo!!.state)
    }
}