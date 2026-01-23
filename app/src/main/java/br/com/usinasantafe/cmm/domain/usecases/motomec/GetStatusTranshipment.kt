package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
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
        return runCatching {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val check = motoMecRepository.hasNoteByIdHeader(id).getOrThrow()
            if (!check) return Result.success(StatusTranshipment.WITHOUT_NOTE)
            val noteLast = motoMecRepository.getNoteLastByIdHeader(id).getOrThrow()
            if(noteLast.idStop == null) return Result.success(StatusTranshipment.WITHOUT_NOTE)
            if((noteLast.nroEquipTranshipment != null ) && (noteLast.dateHour > adjDate(-10))){
                return Result.success(StatusTranshipment.TIME_INVALID)
            }
            StatusTranshipment.OK
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}