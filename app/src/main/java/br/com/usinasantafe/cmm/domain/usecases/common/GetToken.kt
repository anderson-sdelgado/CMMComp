package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
            if (resultGet.isFailure){
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetToken",
                    message = e.message,
                    cause = e.cause
                )
            }
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
            return resultFailure(
                context = "IGetToken",
                message = "-",
                cause = e
            )
        }
    }

}