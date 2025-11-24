package br.com.usinasantafe.cmm.domain.usecases.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.cmm.domain.usecases.config.SetStatusSend
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckSendMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.SendMotoMec
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class ProcessWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkSendMotoMec: CheckSendMotoMec,
    private val sendMotoMec: SendMotoMec,
    private val setStatusSend: SetStatusSend
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val resultMotoMecCheckSend = checkSendMotoMec()
        resultMotoMecCheckSend.onFailure {
            val failure =
                "${getClassAndMethod()} -> ${it.message} -> ${it.cause.toString()}"
            Timber.e(failure)
            return Result.retry()
        }
        val checkHeaderSend = resultMotoMecCheckSend.getOrNull()!!
        if(checkHeaderSend) {
            val resultSendHeader = sendMotoMec()
            resultSendHeader.onFailure {
                val failure =
                    "${getClassAndMethod()} -> ${it.message} -> ${it.cause.toString()}"
                Timber.e(failure)
                return Result.retry()
            }
        }
        setStatusSend(StatusSend.SENT)
        return Result.success()
    }

}

