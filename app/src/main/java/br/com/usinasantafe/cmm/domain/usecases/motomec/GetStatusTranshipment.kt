package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StatusTranshipment
import br.com.usinasantafe.cmm.utils.adjDate
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetStatusTranshipment {
    suspend operator fun invoke(): Result<StatusTranshipment>
}

class IGetStatusTranshipment @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetStatusTranshipment {

    override suspend fun invoke(): Result<StatusTranshipment> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val check = motoMecRepository.hasNoteByIdHeader(id).getOrThrow()
            if (!check) return@call StatusTranshipment.WITHOUT_NOTE
            val noteLast = motoMecRepository.getNoteLastByIdHeader(id).getOrThrow()
            if(noteLast.idStop == null) return@call StatusTranshipment.WITHOUT_NOTE
            if((noteLast.nroEquipTranshipment != null ) && (noteLast.dateHour > adjDate(-10))){
                return@call StatusTranshipment.TIME_INVALID
            }
            StatusTranshipment.OK
        }

}