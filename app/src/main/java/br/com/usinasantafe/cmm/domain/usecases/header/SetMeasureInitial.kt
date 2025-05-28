package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

interface SetMeasureInitial {
    suspend operator fun invoke(
        measure: String
    ): Result<Boolean>
}

class ISetMeasureInitial @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository,
    private val equipRepository: EquipRepository,
    private val saveHeaderOpen: SaveHeaderOpen
): SetMeasureInitial {

    override suspend fun invoke(
        measure: String
    ): Result<Boolean> {
        try {
            val locale = Locale("pt", "BR")
            val formatNumber = NumberFormat.getInstance(locale)
            val measureInput = formatNumber.parse(measure)!!
            val measureInputDouble = measureInput.toDouble()
            val resultSetMeasureInitial = headerMotoMecRepository.setMeasureInitial(measureInputDouble)
            if(resultSetMeasureInitial.isFailure) {
                val e = resultSetMeasureInitial.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultGetIdEquip = headerMotoMecRepository.getIdEquip()
            if(resultGetIdEquip.isFailure) {
                val e = resultGetIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultUpdateMeasureByIdEquip = equipRepository.updateMeasureByIdEquip(
                measure = measureInputDouble,
                idEquip = idEquip
            )
            if(resultUpdateMeasureByIdEquip.isFailure) {
                val e = resultUpdateMeasureByIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultSaveHeaderOpen = saveHeaderOpen()
            if(resultSaveHeaderOpen.isFailure) {
                val e = resultSaveHeaderOpen.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetMeasureInitial",
                message = "-",
                cause = e
            )
        }
    }

}