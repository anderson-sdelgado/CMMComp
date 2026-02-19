package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.usecases.motomec.SaveNote
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetSpeedPressure {
    suspend operator fun invoke(speed: Int): Result<Unit>
}

class ISetSpeedPressure @Inject constructor(
    private val fertigationRepository: FertigationRepository,
    private val saveNote: SaveNote,
    private val startWorkManager: StartWorkManager
): SetSpeedPressure {

    override suspend fun invoke(speed: Int): Result<Unit> =
        call(getClassAndMethod()) {
            fertigationRepository.setSpeedPressure(speed).getOrThrow()
            saveNote().getOrThrow()
            startWorkManager()
        }

}