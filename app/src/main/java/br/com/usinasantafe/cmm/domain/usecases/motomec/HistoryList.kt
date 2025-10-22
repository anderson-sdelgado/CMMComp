package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.utils.FlowNote
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface HistoryList {
    suspend operator fun invoke(): Result<List<ItemHistoryScreenModel>>
}

class IHistoryList @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val activityRepository: ActivityRepository,
    private val stopRepository: StopRepository
): HistoryList {

    override suspend fun invoke(): Result<List<ItemHistoryScreenModel>> {
        try {
            val resultNoteList = motoMecRepository.noteList()
            if(resultNoteList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultNoteList.exceptionOrNull()!!
                )
            }
            val listNoteList = resultNoteList.getOrNull()!!
            val itemHistoryList = listNoteList.map {

                val type = if(it.idStop == null) FlowNote.WORK else FlowNote.STOP

                val descr = if(it.idStop != null) {
                    val resultStop = stopRepository.getById(it.idStop!!)
                    if(resultStop.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultStop.exceptionOrNull()!!
                        )
                    }
                    resultStop.getOrNull()!!.descrStop
                } else {
                    val resultActivity = activityRepository.getById(it.idActivity!!)
                    if(resultActivity.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultActivity.exceptionOrNull()!!
                        )
                    }
                    resultActivity.getOrNull()!!.descrActivity
                }

                val dateHour = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.Builder().setLanguage("pt").setRegion("BR").build()
                ).format(it.dateHour)

                ItemHistoryScreenModel(
                    id = it.id!!,
                    type = type,
                    descr = descr,
                    dateHour = dateHour,
                    detail = ""
                )
            }
            return Result.success(itemHistoryList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}