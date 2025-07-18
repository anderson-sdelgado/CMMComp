package br.com.usinasantafe.cmm.domain.usecases.note

import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdStopNote {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ISetIdStopNote @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val startWorkManager: StartWorkManager
): SetIdStopNote {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = motoMecRepository.setIdStop(id)
            if (resultSet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSet.exceptionOrNull()!!
                )
            }
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            if(resultGetId.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultSave = motoMecRepository.saveNote(idHeader)
            if(resultSave.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            startWorkManager()
            return resultSave
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}