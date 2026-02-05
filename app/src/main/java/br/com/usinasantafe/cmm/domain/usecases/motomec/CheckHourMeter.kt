package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.presenter.model.CheckHourMeterModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.doubleToString
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.stringToDouble
import br.com.usinasantafe.cmm.utils.tryCatch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

interface CheckHourMeter {
    suspend operator fun invoke(
        hourMeter: String,
    ): Result<CheckHourMeterModel>
}

class ICheckHourMeter @Inject constructor(
    private val equipRepository: EquipRepository,
): CheckHourMeter {

    override suspend fun invoke(
        hourMeter: String,
    ): Result<CheckHourMeterModel> =
        call(getClassAndMethod()) {
            val hourMeterInput = tryCatch(::stringToDouble.name) { stringToDouble(hourMeter) }
            val hourMeterBDDouble = equipRepository.getHourMeter().getOrThrow()
            val hourMeterBD = tryCatch(::doubleToString.name) { doubleToString(hourMeterBDDouble) }
            val check = !(hourMeterInput < hourMeterBDDouble)
            CheckHourMeterModel(hourMeterBD = hourMeterBD, check = check)
        }

}