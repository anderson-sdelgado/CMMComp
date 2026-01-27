package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.onFailure

interface HasNroOS {
    suspend operator fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean>
}

class IHasNroOS @Inject constructor(
    private val checkNetwork: CheckNetwork,
    private val osRepository: OSRepository,
    private val rOSActivityRepository: ROSActivityRepository,
    private val getToken: GetToken,
    private val motoMecRepository: MotoMecRepository
): HasNroOS {

    override suspend fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            if(flowApp == FlowApp.HEADER_INITIAL){
                rOSActivityRepository.deleteAll().getOrThrow()
                osRepository.deleteAll().getOrThrow()
            }
            val checkNroOS = osRepository.checkNroOS(nroOS = nroOS.toInt()).getOrThrow()
            if(checkNroOS) return@call true
            if(!checkNetwork.isConnected()) {
                motoMecRepository.setStatusConHeader(false).getOrThrow()
                return@call true
            }
            val token = getToken().getOrThrow()
            val resultGetListOSWeb = osRepository.getListByNroOS(token, nroOS.toInt())
            resultGetListOSWeb.onFailure {
                if (it.cause is SocketTimeoutException) {
                    motoMecRepository.setStatusConHeader(false).getOrThrow()
                    return@call true
                }
                throw it
            }
            val osList = resultGetListOSWeb.getOrNull()!!
            if(osList.isEmpty()) return@call false
            val resultGetListROSActivity = rOSActivityRepository.listByNroOS(
                token = token,
                nroOS = nroOS.toInt()
            )
            resultGetListROSActivity.onFailure {
                if (it.cause is SocketTimeoutException) {
                    motoMecRepository.setStatusConHeader(false).getOrThrow()
                    return@call true
                }
                throw it
            }
            val rOSActivityList = resultGetListROSActivity.getOrNull()!!
            if(rOSActivityList.isEmpty()) return@call false
            osRepository.add(osList[0]).getOrThrow()
            rOSActivityRepository.addAll(rOSActivityList).getOrThrow()
            return@call true
        }

}