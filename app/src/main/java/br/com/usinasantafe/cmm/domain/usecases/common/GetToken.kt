package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.token
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository
): GetToken {

    override suspend fun invoke(): Result<String> {
        return runCatching {
            val entity = configRepository.get().getOrThrow()
            val nroEquip = equipRepository.getNroEquipMain().getOrThrow()
            runCatching {
                fun <T> T?.required(field: String): T =
                    this ?: throw NullPointerException("$field is required")
                token(
                    app = entity.app.required("app"),
                    idServ = entity.idServ.required("idServ"),
                    nroEquip = nroEquip,
                    number = entity.number.required("number"),
                    version = entity.version.required("version")
                )
            }.getOrElse { e ->
                throw Exception("token", e)
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}