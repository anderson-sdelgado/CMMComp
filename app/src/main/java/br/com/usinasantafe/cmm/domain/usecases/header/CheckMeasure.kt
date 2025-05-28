package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

interface CheckMeasure {
    suspend operator fun invoke(
        measure: String,
    ): Result<CheckMeasureModel>
}

class ICheckMeasure @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository,
    private val equipRepository: EquipRepository,
): CheckMeasure {

    override suspend fun invoke(
        measure: String,
    ): Result<CheckMeasureModel> {
        try {
            val measureBD: String
            var check = true
            val locale = Locale("pt", "BR")
            val formatNumber = NumberFormat.getInstance(locale)
            val formatDecimal = DecimalFormat("#,##0.0")
            formatDecimal.decimalFormatSymbols = DecimalFormatSymbols(locale)
            val measureInput = formatNumber.parse(measure)!!
            val measureInputDouble = measureInput.toDouble()
            val resultGetIdEquip = headerMotoMecRepository.getIdEquip()
            if(resultGetIdEquip.isFailure) {
                val e = resultGetIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetMeasureByIdEquip = equipRepository.getMeasureByIdEquip(idEquip)
            if(resultGetMeasureByIdEquip.isFailure) {
                val e = resultGetMeasureByIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val measureBDDouble = resultGetMeasureByIdEquip.getOrNull()!!
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
                context = "ICheckMeasureInitial",
                message = "-",
                cause = e
            )
        }
    }

}