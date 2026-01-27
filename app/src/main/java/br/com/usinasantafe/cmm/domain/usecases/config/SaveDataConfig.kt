package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SaveDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String,
        app: String,
        checkMotoMec: Boolean,
        idServ: Int,
        equip: Equip,
    ): EmptyResult
}

class ISaveDataConfig @Inject constructor(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository
): SaveDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String,
        app: String,
        checkMotoMec: Boolean,
        idServ: Int,
        equip: Equip
    ): EmptyResult =
        call(getClassAndMethod()) {
            val numberLong = tryCatch(::toLong.name) {
                number.toLong()
            }
            val entity = Config(
                number = numberLong,
                password = password,
                version = version,
                app = app.uppercase(),
                checkMotoMec = checkMotoMec,
                idServ = idServ
            )
            configRepository.save(entity).getOrThrow()
            equipRepository.saveEquipMain(equip).getOrThrow()
        }

}