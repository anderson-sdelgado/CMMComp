package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.stringToDouble
import br.com.usinasantafe.cmm.utils.tryCatch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

interface SetHourMeter {
    suspend operator fun invoke(
        hourMeter: String,
        flowApp: FlowApp
    ): Result<FlowApp>
}

class ISetHourMeter @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository,
    private val startWorkManager: StartWorkManager,
    private val configRepository: ConfigRepository,
): SetHourMeter {

    override suspend fun invoke(
        hourMeter: String,
        flowApp: FlowApp
    ): Result<FlowApp> =
        call(getClassAndMethod()) {

            val value = tryCatch(::stringToDouble.name) { stringToDouble(hourMeter) }
            equipRepository.updateHourMeter(value).getOrThrow()

            if(flowApp == FlowApp.HEADER_INITIAL) {
                motoMecRepository.setHourMeterInitialHeader(value).getOrThrow()
                val typeEquip = motoMecRepository.getTypeEquipHeader().getOrThrow()
                if(typeEquip == TypeEquip.REEL_FERT) return@call FlowApp.REEL_FERT
                motoMecRepository.saveHeader().getOrThrow()
                startWorkManager()
                val check = checkOpenCheckList().getOrThrow()
                if(check) return@call FlowApp.CHECK_LIST
                return@call flowApp
            }

            motoMecRepository.setHourMeterFinishHeader(value).getOrThrow()

            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val check = motoMecRepository.hasPerformanceByIdHeader(id).getOrThrow()

            motoMecRepository.finishHeader().getOrThrow()

            startWorkManager()
            return@call flowApp
        }

    private suspend fun checkOpenCheckList(): Result<Boolean> =
        call(getClassAndMethod()) {

            val idCheckList = equipRepository.getIdCheckList().getOrThrow()
            if (idCheckList == 0) return@call false

            val idTurnCheckListLastOrNull = configRepository.getIdTurnCheckListLast().getOrThrow()
            val idTurnCheckListLast = idTurnCheckListLastOrNull ?: return@call true

            val idTurnHeader = motoMecRepository.getIdTurnHeader().getOrThrow()
            if (idTurnHeader != idTurnCheckListLast) return@call true

            val dateCheckListLast = configRepository.getDateCheckListLast().getOrThrow()
            val dateNow = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return@call dateFormat.format(dateNow) != dateFormat.format(dateCheckListLast)
        }

}