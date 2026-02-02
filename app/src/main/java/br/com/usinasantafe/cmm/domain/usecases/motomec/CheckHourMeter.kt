package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

interface CheckHourMeter {
    suspend operator fun invoke(
        hourMeter: String,
    ): Result<CheckMeasureModel>
}

class ICheckHourMeter @Inject constructor(
    private val equipRepository: EquipRepository,
): CheckHourMeter {

    override suspend fun invoke(
        hourMeter: String,
    ): Result<CheckMeasureModel> =
        call(getClassAndMethod()) {
            val measureBD: String
            var check = true
            val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
            val formatNumber = NumberFormat.getInstance(locale)
            val formatDecimal = DecimalFormat("#,##0.0")
            formatDecimal.decimalFormatSymbols = DecimalFormatSymbols(locale)
            val hourMeterInput = formatNumber.parse(hourMeter)!!
            val hourMeterInputDouble = hourMeterInput.toDouble()
            val hourMeterBDDouble = equipRepository.getHourMeter().getOrThrow()
            measureBD = formatDecimal.format(hourMeterBDDouble)
            if(hourMeterInputDouble < hourMeterBDDouble) check = false
            CheckMeasureModel(measureBD = measureBD, check = check)
        }

}