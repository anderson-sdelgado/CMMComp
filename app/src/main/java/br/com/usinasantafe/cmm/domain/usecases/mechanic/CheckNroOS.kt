package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ITEM_OS_MECHANIC
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.emitProgressOS
import br.com.usinasantafe.cmm.utils.emitProgressOSError
import br.com.usinasantafe.cmm.utils.flowCall
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedBytes.toInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

interface CheckNroOS {
    suspend operator fun invoke(
        nroOS: String,
    ): Flow<UpdateStatusState>
}

class ICheckNroOS @Inject constructor(
    private val checkNetwork: CheckNetwork,
    private val equipRepository: EquipRepository,
    private val itemOSMechanicRepository: ItemOSMechanicRepository,
    private val getToken: GetToken
): CheckNroOS {

    override suspend fun invoke(
        nroOS: String,
    ): Flow<UpdateStatusState> = flow {
        flowCall(getClassAndMethod()) {

            emitProgressOS(LevelUpdate.CHECK_CONNECTION, TB_ITEM_OS_MECHANIC)
            if (!checkNetwork.isConnected()) {
                emitProgressOSError(errors = Errors.FAILURE_CONNECTION, checkDialog = false)
                return@flowCall
            }
            val nroOSInt = tryCatch(::toInt.name) {
                nroOS.toInt()
            }
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            val token = getToken().getOrThrow()

            emitProgressOS(LevelUpdate.RECOVERY, TB_ITEM_OS_MECHANIC)
            val resultList =
                itemOSMechanicRepository.listByIdEquipAndNroOS(token, idEquip, nroOSInt)
            resultList.onFailure {
                if (it.cause is SocketTimeoutException){
                    emitProgressOSError(errors = Errors.TIME_OUT, checkDialog = false)
                    return@flowCall
                }
                throw it
            }
            val list = resultList.getOrThrow()
            if (list.isEmpty()) {
                emitProgressOSError(errors = Errors.INVALID)
                return@flowCall
            }

            emitProgressOS(LevelUpdate.CLEAN, TB_ITEM_OS_MECHANIC)
            itemOSMechanicRepository.deleteAll().getOrThrow()

            emitProgressOS(LevelUpdate.SAVE, TB_ITEM_OS_MECHANIC)
            itemOSMechanicRepository.addAll(list).getOrThrow()
        }

    }

}