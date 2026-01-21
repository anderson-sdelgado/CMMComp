package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
        try {
            val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
            val formatNumber = NumberFormat.getInstance(locale)
            val hourMeterInput = formatNumber.parse(hourMeter)!!
            val hourMeterInputDouble = hourMeterInput.toDouble()

            val resultUpdateHourMeterByIdEquip = equipRepository.updateHourMeter(
                hourMeter = hourMeterInputDouble
            )
            resultUpdateHourMeterByIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }

            if(flowApp == FlowApp.HEADER_INITIAL) {
                val resultSetHourMeter = motoMecRepository.setHourMeterInitialHeader(hourMeterInputDouble)
                resultSetHourMeter.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val resultGetTypeEquip = motoMecRepository.getTypeEquipHeader()
                resultGetTypeEquip.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val typeEquip = resultGetTypeEquip.getOrNull()!!
                if(typeEquip == TypeEquip.REEL_FERT) return Result.success(FlowApp.REEL_FERT)
                val resultSaveHeader = motoMecRepository.saveHeader()
                resultSaveHeader.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                startWorkManager()
                val resultCheckOpenCheckList = checkOpenCheckList()
                resultCheckOpenCheckList.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val check = resultCheckOpenCheckList.getOrNull()!!
                if(check) return Result.success(FlowApp.CHECK_LIST)
                return Result.success(flowApp)
            }

            val resultSetHourMeter = motoMecRepository.setHourMeterFinishHeader(hourMeterInputDouble)
            resultSetHourMeter.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            resultSetHourMeter.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultFinishHeader = motoMecRepository.finishHeader()
            resultFinishHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            startWorkManager()
            return Result.success(flowApp)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    private suspend fun checkOpenCheckList(): Result<Boolean> {
        try {
            val resultGetIdCheckList = equipRepository.getIdCheckList()
            resultGetIdCheckList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if (resultGetIdCheckList.getOrNull()!! == 0) return Result.success(false)
            val resultGetIdTurnCheckListLast = configRepository.getIdTurnCheckListLast()
            resultGetIdTurnCheckListLast.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idTurnCheckListLast = resultGetIdTurnCheckListLast.getOrNull() ?: return Result.success(true)
            val resultGetIdTurnHeader = motoMecRepository.getIdTurnHeader()
            resultGetIdTurnHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idTurnHeader = resultGetIdTurnHeader.getOrNull()!!
            if (idTurnHeader != idTurnCheckListLast) return Result.success(true)
            val resultGetDateCheckListLast = configRepository.getDateCheckListLast()
            resultGetDateCheckListLast.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val dateNow = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateCheckListLast = resultGetDateCheckListLast.getOrNull()!!
            if(dateFormat.format(dateNow) == dateFormat.format(dateCheckListLast)) return Result.success(false)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}