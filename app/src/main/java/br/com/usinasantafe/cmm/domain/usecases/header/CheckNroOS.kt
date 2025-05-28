package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.CheckNetwork
import java.net.SocketTimeoutException
import javax.inject.Inject

interface CheckNroOS {
    suspend operator fun invoke(nroOS: String): Result<Boolean>
}

class ICheckNroOS @Inject constructor(
    private val checkNetwork: CheckNetwork,
    private val osRepository: OSRepository,
    private val rOSActivityRepository: ROSActivityRepository,
    private val getToken: GetToken
): CheckNroOS {

    override suspend fun invoke(nroOS: String): Result<Boolean> {
        return try {
            if(!checkNetwork.isConnected()) return Result.success(true)
            val resultGetToken = getToken()
            if(resultGetToken.isFailure) {
                val e = resultGetToken.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGetListOS = osRepository.getListByNroOS(
                    token = token,
                    nroOS = nroOS.toInt()
                )
            if(resultGetListOS.isFailure) {
                val e = resultGetListOS.exceptionOrNull()!!
                if (e.cause is SocketTimeoutException) return Result.success(true)
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val osList = resultGetListOS.getOrNull()!!
            if(osList.isEmpty()) return Result.success(false)
            val resultGetListROSActivity = rOSActivityRepository.getListByNroOS(
                token = token,
                nroOS = nroOS.toInt()
            )
            if(resultGetListROSActivity.isFailure) {
                val e = resultGetListROSActivity.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val rOSActivityList = resultGetListROSActivity.getOrNull()!!
            if(rOSActivityList.isEmpty()) return Result.success(false)
            val resultOSDeleteAll = osRepository.deleteAll()
            if(resultOSDeleteAll.isFailure) {
                val e = resultOSDeleteAll.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultOSAdd = osRepository.add(osList[0])
            if(resultOSAdd.isFailure) {
                val e = resultOSAdd.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultROSAtivDeleteAll = rOSActivityRepository.deleteAll()
            if(resultROSAtivDeleteAll.isFailure) {
                val e = resultROSAtivDeleteAll.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultROSAtivAddAll = rOSActivityRepository.addAll(rOSActivityList)
            if(resultROSAtivAddAll.isFailure) {
                val e = resultROSAtivAddAll.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            resultFailure(
                context = "ICheckNroOS",
                message = "-",
                cause = e
            )
        }
    }

}