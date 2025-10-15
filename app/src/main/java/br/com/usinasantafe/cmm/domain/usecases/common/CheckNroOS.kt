package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.net.SocketTimeoutException
import javax.inject.Inject

interface CheckNroOS {
    suspend operator fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean>
}

class ICheckNroOS @Inject constructor(
    private val checkNetwork: CheckNetwork,
    private val osRepository: OSRepository,
    private val rOSActivityRepository: ROSActivityRepository,
    private val getToken: GetToken,
    private val motoMecRepository: MotoMecRepository
): CheckNroOS {

    override suspend fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean> {
        return try {
            if(flowApp == FlowApp.HEADER_INITIAL){
                val resultROSActivityDeleteAll = rOSActivityRepository.deleteAll()
                if(resultROSActivityDeleteAll.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultROSActivityDeleteAll.exceptionOrNull()!!
                    )
                }
                val resultOSDeleteAll = osRepository.deleteAll()
                if(resultOSDeleteAll.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultOSDeleteAll.exceptionOrNull()!!
                    )
                }
            }
            val resultCheckNro = osRepository.checkNroOS(
                nroOS = nroOS.toInt()
            )
            if(resultCheckNro.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckNro.exceptionOrNull()!!
                )
            }
            val checkNroOS = resultCheckNro.getOrNull()!!
            if(checkNroOS) return Result.success(true)
            if(!checkNetwork.isConnected()) {
                val resultSetStatusCon = motoMecRepository.setStatusConHeader(false)
                if(resultSetStatusCon.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultSetStatusCon.exceptionOrNull()!!
                    )
                }
                return Result.success(true)
            }
            val resultGetToken = getToken()
            if(resultGetToken.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetToken.exceptionOrNull()!!
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGetListOSWeb = osRepository.getListByNroOS(
                    token = token,
                    nroOS = nroOS.toInt()
                )
            if(resultGetListOSWeb.isFailure) {
                val e = resultGetListOSWeb.exceptionOrNull()!!
                if (e.cause is SocketTimeoutException) {
                    val resultSetStatusCon = motoMecRepository.setStatusConHeader(false)
                    if(resultSetStatusCon.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultSetStatusCon.exceptionOrNull()!!
                        )
                    }
                    return Result.success(true)
                }
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetListOSWeb.exceptionOrNull()!!
                )
            }
            val osList = resultGetListOSWeb.getOrNull()!!
            if(osList.isEmpty()) return Result.success(false)
            val resultGetListROSActivity = rOSActivityRepository.listByNroOS(
                token = token,
                nroOS = nroOS.toInt()
            )
            if(resultGetListROSActivity.isFailure) {
                val e = resultGetListROSActivity.exceptionOrNull()!!
                if (e.cause is SocketTimeoutException) {
                    val resultSetStatusCon = motoMecRepository.setStatusConHeader(false)
                    if(resultSetStatusCon.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultSetStatusCon.exceptionOrNull()!!
                        )
                    }
                    return Result.success(true)
                }
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetListROSActivity.exceptionOrNull()!!
                )
            }
            val rOSActivityList = resultGetListROSActivity.getOrNull()!!
            if(rOSActivityList.isEmpty()) return Result.success(false)
            val resultOSAdd = osRepository.add(osList[0])
            if(resultOSAdd.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultOSAdd.exceptionOrNull()!!
                )
            }
            val resultROSAtivAddAll = rOSActivityRepository.addAll(rOSActivityList)
            if(resultROSAtivAddAll.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultROSAtivAddAll.exceptionOrNull()!!
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