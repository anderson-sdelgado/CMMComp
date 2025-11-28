package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.onFailure

interface SetHourMeter {
    suspend operator fun invoke(
        hourMeter: String,
        flowApp: FlowApp = FlowApp.HEADER_INITIAL
    ): Result<Boolean>
}

class ISetHourMeter @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository,
    private val startWorkManager: StartWorkManager
): SetHourMeter {

    override suspend fun invoke(
        hourMeter: String,
        flowApp: FlowApp
    ): Result<Boolean> {
        try {
            val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
            val formatNumber = NumberFormat.getInstance(locale)
            val hourMeterInput = formatNumber.parse(hourMeter)!!
            val hourMeterInputDouble = hourMeterInput.toDouble()
            val resultSetHourMeter = if(flowApp == FlowApp.HEADER_INITIAL) {
                motoMecRepository.setHourMeterInitialHeader(hourMeterInputDouble)
            } else {
                motoMecRepository.setHourMeterFinishHeader(hourMeterInputDouble)
            }
            resultSetHourMeter.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultGetIdEquip = motoMecRepository.getIdEquipHeader()
            resultGetIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultUpdateHourMeterByIdEquip = equipRepository.updateHourMeterByIdEquip(
                hourMeter = hourMeterInputDouble,
                idEquip = idEquip
            )
            resultUpdateHourMeterByIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) {
                val resultSaveHeader = motoMecRepository.saveHeader()
                resultSaveHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                startWorkManager()
                return resultSaveHeader
            }
            val resultFinishHeader = motoMecRepository.finishHeader()
            resultFinishHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            startWorkManager()
            return resultFinishHeader
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}