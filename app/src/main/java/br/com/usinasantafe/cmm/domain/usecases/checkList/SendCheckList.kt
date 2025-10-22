package br.com.usinasantafe.cmm.domain.usecases.checkList

import javax.inject.Inject

interface SendCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendCheckList @Inject constructor(
): SendCheckList {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}