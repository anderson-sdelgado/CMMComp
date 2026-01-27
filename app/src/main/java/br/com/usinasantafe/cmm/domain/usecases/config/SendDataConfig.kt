package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.tryCatch
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        nroEquip: String,
        app: String,
        version: String,
    ): Result<Config>
}

class ISendDataConfig @Inject constructor(
    private val configRepository: ConfigRepository
): SendDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        nroEquip: String,
        app: String,
        version: String,
    ): Result<Config> =
        call(getClassAndMethod()) {

            val numberLong = tryCatch("number.toLong") {
                number.toLong()
            }

            val nroEquipLong = tryCatch("nroEquip.toLong") {
                nroEquip.toLong()
            }

            val entity = Config(
                number = numberLong,
                password = password,
                nroEquip = nroEquipLong,
                app = app.uppercase(),
                version = version
            )
            configRepository.send(entity).getOrThrow()
        }

}