package br.com.usinasantafe.cmm.domain.usecases.note

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
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
                val e = resultSet.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdStopNote",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultGetId = motoMecRepository.getIdByOpenHeader()
            if(resultGetId.isFailure){
                val e = resultGetId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdStopNote",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultSave = motoMecRepository.saveNote(idHeader)
            if(resultSave.isFailure){
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdStopNote",
                    message = e.message,
                    cause = e.cause
                )
            }
            startWorkManager()
            return resultSave
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetIdStopNote",
                message = "-",
                cause = e
            )
        }
    }

}