package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.utils.FlowApp
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
                val e = resultSetHourMeter.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetHourMeter",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultGetIdEquip = motoMecRepository.getIdEquipHeader()
            if(resultGetIdEquip.isFailure) {
                val e = resultGetIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetHourMeter",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultUpdateMeasureByIdEquip = equipRepository.updateHourMeterByIdEquip(
                hourMeter = hourMeterInputDouble,
                idEquip = idEquip
            )
            if(resultUpdateMeasureByIdEquip.isFailure) {
                val e = resultUpdateMeasureByIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetHourMeter",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) {
                val resultSaveHeader = motoMecRepository.saveHeader()
                if(resultSaveHeader.isFailure) {
                    val e = resultSaveHeader.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISetHourMeter",
                        message = e.message,
                        cause = e.cause
                    )
                }
                startWorkManager()
                return resultSaveHeader
            }
            val resultFinishHeader = motoMecRepository.finishHeader()
            if(resultFinishHeader.isFailure) {
                val e = resultFinishHeader.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetHourMeter",
                    message = e.message,
                    cause = e.cause
                )
            }
            startWorkManager()
            return resultFinishHeader
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetHourMeter",
                message = "-",
                cause = e
            )
        }
    }

}