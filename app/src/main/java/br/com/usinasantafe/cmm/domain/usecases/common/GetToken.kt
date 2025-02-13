package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.token
import javax.inject.Inject

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken @Inject constructor(
    private val configRepository: ConfigRepository
): GetToken {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGet = configRepository.get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val entity = resultGet.getOrNull()!!
            val token = token(
                app = entity.app!!,
                idBD = entity.idBD!!,
                nroEquip = entity.nroEquip!!,
                number = entity.number!!,
                version = entity.version!!
            )
            return Result.success(token)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetToken",
                    cause = e
                )
            )
        }
    }

}