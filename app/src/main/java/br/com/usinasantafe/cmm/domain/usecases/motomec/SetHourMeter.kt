package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.onFailure

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
    private val configRepository: ConfigRepository
): SetHourMeter {

    override suspend fun invoke(
        hourMeter: String,
        flowApp: FlowApp
    ): Result<FlowApp> {
        return runCatching {
            val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
            val formatNumber = NumberFormat.getInstance(locale)
            val hourMeterInput = formatNumber.parse(hourMeter)!!
            val hourMeterInputDouble = hourMeterInput.toDouble()
            equipRepository.updateHourMeter(hourMeterInputDouble).getOrThrow()
            if(flowApp == FlowApp.HEADER_INITIAL) {
                motoMecRepository.setHourMeterInitialHeader(hourMeterInputDouble).getOrThrow()
                val typeEquip = motoMecRepository.getTypeEquipHeader().getOrThrow()
                if(typeEquip == TypeEquip.REEL_FERT) return Result.success(FlowApp.REEL_FERT)
                motoMecRepository.saveHeader().getOrThrow()
                startWorkManager()
                val check = checkOpenCheckList().getOrThrow()
                if(check) return Result.success(FlowApp.CHECK_LIST)
                return Result.success(flowApp)
            }
            motoMecRepository.setHourMeterFinishHeader(hourMeterInputDouble).getOrThrow()
            motoMecRepository.finishHeader().getOrThrow()
            startWorkManager()
            flowApp
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    private suspend fun checkOpenCheckList(): Result<Boolean> {
        return runCatching {
            val idCheckList = equipRepository.getIdCheckList().getOrThrow()
            if (idCheckList == 0) return Result.success(false)
            val idTurnCheckListLastOrNull = configRepository.getIdTurnCheckListLast().getOrThrow()
            val idTurnCheckListLast = idTurnCheckListLastOrNull ?: return Result.success(true)
            val idTurnHeader = motoMecRepository.getIdTurnHeader().getOrThrow()
            if (idTurnHeader != idTurnCheckListLast) return Result.success(true)
            val dateCheckListLast = configRepository.getDateCheckListLast().getOrThrow()
            val dateNow = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.format(dateNow) != dateFormat.format(dateCheckListLast)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}