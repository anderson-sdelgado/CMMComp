package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

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
            val locale = Locale("pt", "BR")
            val formatNumber = NumberFormat.getInstance(locale)
            val hourMeterInput = formatNumber.parse(hourMeter)!!
            val hourMeterInputDouble = hourMeterInput.toDouble()
            val resultSetHourMeter = if(flowApp == FlowApp.HEADER_INITIAL) {
                motoMecRepository.setHourMeterInitialHeader(hourMeterInputDouble)
            } else {
                motoMecRepository.setHourMeterFinishHeader(hourMeterInputDouble)
            }
            if(resultSetHourMeter.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSetHourMeter.exceptionOrNull()!!
                )
            }
            val resultGetIdEquip = motoMecRepository.getIdEquipHeader()
            if(resultGetIdEquip.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquip.exceptionOrNull()!!
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultUpdateHourMeterByIdEquip = equipRepository.updateHourMeterByIdEquip(
                hourMeter = hourMeterInputDouble,
                idEquip = idEquip
            )
            if(resultUpdateHourMeterByIdEquip.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultUpdateHourMeterByIdEquip.exceptionOrNull()!!
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) {
                val resultSaveHeader = motoMecRepository.saveHeader()
                if(resultSaveHeader.isFailure) {
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = resultSaveHeader.exceptionOrNull()!!
                    )
                }
                startWorkManager()
                return resultSaveHeader
            }
            val resultFinishHeader = motoMecRepository.finishHeader()
            if(resultFinishHeader.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultFinishHeader.exceptionOrNull()!!
                )
            }
            startWorkManager()
            return resultFinishHeader
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}