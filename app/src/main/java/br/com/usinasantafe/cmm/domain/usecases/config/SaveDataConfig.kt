package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedBytes.toInt
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
    ): EmptyResult {
        return runCatching {
            val numberLong = runCatching {
                number.toLong()
            }.getOrElse { e ->
                throw Exception(::toLong.name, e)
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
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}