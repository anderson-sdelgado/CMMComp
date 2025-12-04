package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StatusTranshipment
import br.com.usinasantafe.cmm.utils.adjDate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetStatusTranshipment {
    suspend operator fun invoke(): Result<StatusTranshipment>
}

class IGetStatusTranshipment @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetStatusTranshipment {

    override suspend fun invoke(): Result<StatusTranshipment> {
        try {
            val resultGetId = motoMecRepository.getIdByHeaderOpen() //ok
            resultGetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val id = resultGetId.getOrNull()!!
            val resultHasNote = motoMecRepository.hasNoteByIdHeader(id) //ok
            resultHasNote.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val check = resultHasNote.getOrNull()!!
            if (!check) return Result.success(StatusTranshipment.WITHOUT_NOTE)
            val resultGetNoteLast = motoMecRepository.getNoteLastByIdHeader(id) //ok
            resultGetNoteLast.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val noteLast = resultGetNoteLast.getOrNull()!!
            if(noteLast.idStop == null) return Result.success(StatusTranshipment.WITHOUT_NOTE)
            if((noteLast.idEquipTrans != null ) && (noteLast.dateHour > adjDate(-10))){
                return Result.success(StatusTranshipment.TIME_INVALID)
            }
            return Result.success(StatusTranshipment.OK)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}