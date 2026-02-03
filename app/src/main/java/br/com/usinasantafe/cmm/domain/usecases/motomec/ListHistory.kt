package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.lib.STOP
import br.com.usinasantafe.cmm.lib.WORK
import br.com.usinasantafe.cmm.lib.functionListPMM
import br.com.usinasantafe.cmm.utils.call
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

    override suspend fun invoke(): Result<List<ItemHistoryScreenModel>> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val listNoteList = motoMecRepository.listNoteByIdHeader(id).getOrThrow()
            listNoteList.map { item ->
                val function = if(item.idStop == null) {
                    functionListPMM.find { it.second == WORK }!!
                } else {
                    functionListPMM.find { it.second == STOP }!!
                }
                val descr = if(item.idStop != null) {
                    stopRepository.getById(item.idStop!!).getOrThrow().descrStop
                } else {
                    activityRepository.getById(item.idActivity!!).getOrThrow().descrActivity
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
        }

}