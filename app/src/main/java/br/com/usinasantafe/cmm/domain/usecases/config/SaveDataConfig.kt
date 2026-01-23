package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
    ): Result<Boolean>
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
    ): Result<Boolean> {
        try {
            val entity = Config(
                number = number.toLong(),
                password = password,
                version = version,
                app = app.uppercase(),
                checkMotoMec = checkMotoMec,
                idServ = idServ
            )
            val resultSaveConfig = configRepository.save(entity)
            resultSaveConfig.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultSaveEquip = equipRepository.saveEquipMain(equip)
            resultSaveEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return resultSaveEquip
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}