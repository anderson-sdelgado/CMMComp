package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.utils.STOP
import br.com.usinasantafe.cmm.utils.WORK
import br.com.usinasantafe.cmm.utils.functionListPMM
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface ListHistory {
    suspend operator fun invoke(): Result<List<ItemHistoryScreenModel>>
}

class IListHistory @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val activityRepository: ActivityRepository,
    private val stopRepository: StopRepository
): ListHistory {

    override suspend fun invoke(): Result<List<ItemHistoryScreenModel>> {
        try {
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            resultGetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val id = resultGetId.getOrNull()!!
            val resultNoteList = motoMecRepository.noteListByIdHeader(id)
            resultNoteList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val listNoteList = resultNoteList.getOrNull()!!
            val itemHistoryList = listNoteList.map { item ->

                val function = if(item.idStop == null) {
                    functionListPMM.find { it.second == WORK }!!
                } else {
                    functionListPMM.find { it.second == STOP }!!
                }
                val descr = if(item.idStop != null) {
                    val resultStop = stopRepository.getById(item.idStop!!)
                    resultStop.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                    resultStop.getOrNull()!!.descrStop
                } else {
                    val resultActivity = activityRepository.getById(item.idActivity!!)
                    resultActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                    resultActivity.getOrNull()!!.descrActivity
                }
                val dateHour = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.Builder().setLanguage("pt").setRegion("BR").build()
                ).format(item.dateHour)

                ItemHistoryScreenModel(
                    id = item.id!!,
                    function = function,
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