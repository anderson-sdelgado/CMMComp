package br.com.usinasantafe.cmm.domain.usecases.checkList

import javax.inject.Inject

interface CheckSendCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendCheckList @Inject constructor(
): CheckSendCheckList {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}