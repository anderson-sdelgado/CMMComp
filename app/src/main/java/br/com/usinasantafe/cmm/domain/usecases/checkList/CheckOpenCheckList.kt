package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

interface CheckOpenCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckOpenCheckList @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository
): CheckOpenCheckList {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetIdEquip = configRepository.getIdEquip()
            if (resultGetIdEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquip.exceptionOrNull()!!
                )
            }
            val resultGetIdCheckList = equipRepository.getIdCheckListByIdEquip(resultGetIdEquip.getOrNull()!!)
            if (resultGetIdCheckList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdCheckList.exceptionOrNull()!!
                )
            }
            if (resultGetIdCheckList.getOrNull()!! == 0) return Result.success(false)
            val resultGetIdTurnCheckListLast = configRepository.getIdTurnCheckListLast()
            if (resultGetIdTurnCheckListLast.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdTurnCheckListLast.exceptionOrNull()!!
                )
            }
            val idTurnCheckListLast = resultGetIdTurnCheckListLast.getOrNull() ?: return Result.success(true)
            val resultGetIdTurnHeader = motoMecRepository.getIdTurnHeader()
            if (resultGetIdTurnHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdTurnHeader.exceptionOrNull()!!
                )
            }
            val idTurnHeader = resultGetIdTurnHeader.getOrNull()!!
            if (idTurnHeader != idTurnCheckListLast) return Result.success(true)
            val resultGetDateCheckListLast = configRepository.getDateCheckListLast()
            if (resultGetDateCheckListLast.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetDateCheckListLast.exceptionOrNull()!!
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