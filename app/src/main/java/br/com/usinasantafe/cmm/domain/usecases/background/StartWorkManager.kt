package br.com.usinasantafe.cmm.domain.usecases.background

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.usinasantafe.cmm.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.cmm.domain.usecases.config.SetStatusSend
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface StartWorkManager {
    suspend operator fun invoke()
}

class IStartWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val getConfigInternal: GetConfigInternal,
    private val setStatusSend: SetStatusSend
): StartWorkManager {

    override suspend fun invoke() {
        val resultGet = getConfigInternal()
        resultGet.onFailure {
            val failure =
                "${getClassAndMethod()} -> ${it.message} -> ${it.cause.toString()}"
            Timber.e(failure)
            return
        }
        if(resultGet.getOrNull() == null) return
        setStatusSend(StatusSend.SEND)
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(ProcessWorkManager::class.java)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                2, TimeUnit.MINUTES
            )
            .build()
        workManager.enqueueUniqueWork("WORK-MANAGER-CMM", ExistingWorkPolicy.REPLACE, workRequest)
    }

}