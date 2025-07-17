package br.com.usinasantafe.cmm.domain.usecases.checkList

import javax.inject.Inject

interface DelLastRespItemCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class IDelLastRespItemCheckList @Inject constructor(
): DelLastRespItemCheckList {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}