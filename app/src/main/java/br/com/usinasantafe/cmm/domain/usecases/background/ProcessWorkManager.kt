package br.com.usinasantafe.cmm.domain.usecases.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.cmm.domain.usecases.config.SetStatusSend
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckSendMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.SendHeader
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
    private val sendHeader: SendHeader,
    private val setStatusSend: SetStatusSend
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val resultMotoMecCheckSend = checkSendMotoMec()
        if (resultMotoMecCheckSend.isFailure) {
            val error = resultMotoMecCheckSend.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return Result.retry()
        }
        val checkHeaderSend = resultMotoMecCheckSend.getOrNull()!!
        if(checkHeaderSend) {
            val resultSendHeader = sendHeader()
            if (resultSendHeader.isFailure){
                val error = resultSendHeader.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                return Result.retry()
            }
        }
        setStatusSend(StatusSend.SENT)
        return Result.success()
    }

}

