package br.com.usinasantafe.cmm.domain.usecases.background

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.cmm.domain.usecases.config.SetStatusSend
import br.com.usinasantafe.cmm.domain.usecases.header.CheckHeaderSend
import br.com.usinasantafe.cmm.domain.usecases.header.SendHeader
import br.com.usinasantafe.cmm.utils.StatusSend

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProcessWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkHeaderSend: CheckHeaderSend,
    private val sendHeader: SendHeader,
    private val setStatusSend: SetStatusSend
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val resultCheckHeaderSend = checkHeaderSend()
        val checkHeaderSend = resultCheckHeaderSend.getOrNull()!!
        if(checkHeaderSend) {
            val resultSendHeader = sendHeader()
            if (resultSendHeader.isFailure)
                return Result.retry()
        }
        setStatusSend(StatusSend.SENT)
        return Result.success()
    }

}

