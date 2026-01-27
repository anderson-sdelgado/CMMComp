package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.required
import br.com.usinasantafe.cmm.utils.token
import br.com.usinasantafe.cmm.utils.tryCatch
import javax.inject.Inject

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository
): GetToken {

    override suspend fun invoke(): Result<String> =
        call(getClassAndMethod()) {
            val entity = configRepository.get().getOrThrow()
            val nroEquip = equipRepository.getNroEquipMain().getOrThrow()
            tryCatch("token") {
                token(
                    app = entity.app.required("app"),
                    idServ = entity.idServ.required("idServ"),
                    nroEquip = nroEquip,
                    number = entity.number.required("number"),
                    version = entity.version.required("version")
                )
            }
        }

}