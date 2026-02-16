package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdStopNote {
    suspend operator fun invoke(
        id: Int
    ): EmptyResult
}

class ISetIdStopNote @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val startWorkManager: StartWorkManager,
    private val saveNote: SaveNote
): SetIdStopNote {

    override suspend fun invoke(
        id: Int
    ): EmptyResult =
        call(getClassAndMethod()) {

            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()

            motoMecRepository.setIdStop(id).getOrThrow()
            val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
            saveNote(idHeader, idActivity, nroOS).getOrThrow()

            startWorkManager()

        }

}