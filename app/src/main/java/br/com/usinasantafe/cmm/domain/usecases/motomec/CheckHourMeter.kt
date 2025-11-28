package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
            resultGetIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetHourMeterByIdEquip = equipRepository.getHourMeterByIdEquip(idEquip)
            resultGetHourMeterByIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}