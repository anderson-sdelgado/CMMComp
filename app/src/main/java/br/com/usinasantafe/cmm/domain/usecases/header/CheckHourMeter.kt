package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

interface CheckHourMeter {
    suspend operator fun invoke(
        measure: String,
    ): Result<CheckMeasureModel>
}

class ICheckHourMeter @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository,
): CheckHourMeter {

    override suspend fun invoke(
        measure: String,
    ): Result<CheckMeasureModel> {
        try {
            val measureBD: String
            var check = true
            val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
            val formatNumber = NumberFormat.getInstance(locale)
            val formatDecimal = DecimalFormat("#,##0.0")
            formatDecimal.decimalFormatSymbols = DecimalFormatSymbols(locale)
            val measureInput = formatNumber.parse(measure)!!
            val measureInputDouble = measureInput.toDouble()
            val resultGetIdEquip = motoMecRepository.getIdEquipHeader()
            if(resultGetIdEquip.isFailure) {
                val e = resultGetIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckHourMeter",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetHourMeterByIdEquip = equipRepository.getHourMeterByIdEquip(idEquip)
            if(resultGetHourMeterByIdEquip.isFailure) {
                val e = resultGetHourMeterByIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckHourMeter",
                    message = e.message,
                    cause = e.cause
                )
            }
            val measureBDDouble = resultGetHourMeterByIdEquip.getOrNull()!!
            measureBD = formatDecimal.format(measureBDDouble)
            if(measureInputDouble < measureBDDouble) check = false
            return Result.success(
                CheckMeasureModel(
                    measureBD = measureBD,
                    check = check
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckHourMeter",
                message = "-",
                cause = e
            )
        }
    }

}