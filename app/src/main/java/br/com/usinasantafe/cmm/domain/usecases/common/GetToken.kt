package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.token
import javax.inject.Inject

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository
): GetToken {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGet = configRepository.get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val entity = resultGet.getOrNull()!!
            val resultGetNroEquip = equipRepository.getNroEquipMain()
            resultGetNroEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val nroEquip = resultGetNroEquip.getOrNull()!!
            val token = token(
                app = entity.app!!,
                idServ = entity.idServ!!,
                nroEquip = nroEquip,
                number = entity.number!!,
                version = entity.version!!
            )
            return Result.success(token)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}