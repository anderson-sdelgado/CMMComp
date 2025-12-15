package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.lib.FlowApp
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
    ): Result<Boolean> {
        return try {
            if(flowApp == FlowApp.HEADER_INITIAL){
                val resultROSActivityDeleteAll = rOSActivityRepository.deleteAll()
                resultROSActivityDeleteAll.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                val resultOSDeleteAll = osRepository.deleteAll()
                resultOSDeleteAll.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            }
            val resultCheckNro = osRepository.checkNroOS(
                nroOS = nroOS.toInt()
            )
            resultCheckNro.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val checkNroOS = resultCheckNro.getOrNull()!!
            if(checkNroOS) return Result.success(true)
            if(!checkNetwork.isConnected()) {
                val resultSetStatusCon = motoMecRepository.setStatusConHeader(false)
                resultSetStatusCon.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                return Result.success(true)
            }
            val resultGetToken = getToken()
            resultGetToken.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGetListOSWeb = osRepository.getListByNroOS(
                    token = token,
                    nroOS = nroOS.toInt()
                )
            resultGetListOSWeb.onFailure {
                if (it.cause is SocketTimeoutException) {
                    val resultSetStatusCon = motoMecRepository.setStatusConHeader(false)
                    resultSetStatusCon.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                    return Result.success(true)
                }
                return Result.failure(it)
            }
            val osList = resultGetListOSWeb.getOrNull()!!
            if(osList.isEmpty()) return Result.success(false)
            val resultGetListROSActivity = rOSActivityRepository.listByNroOS(
                token = token,
                nroOS = nroOS.toInt()
            )
            resultGetListROSActivity.onFailure {
                if (it.cause is SocketTimeoutException) {
                    val resultSetStatusCon = motoMecRepository.setStatusConHeader(false)
                    resultSetStatusCon.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                    return Result.success(true)
                }
                return Result.failure(it)
            }
            val rOSActivityList = resultGetListROSActivity.getOrNull()!!
            if(rOSActivityList.isEmpty()) return Result.success(false)
            val resultOSAdd = osRepository.add(osList[0])
            resultOSAdd.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultROSActivityAddAll = rOSActivityRepository.addAll(rOSActivityList)
            resultROSActivityAddAll.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}