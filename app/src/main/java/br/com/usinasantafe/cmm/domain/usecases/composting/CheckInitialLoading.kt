package br.com.usinasantafe.cmm.domain.usecases.composting

import javax.inject.Inject

interface CheckInitialLoading {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckInitialLoading @Inject constructor(
): CheckInitialLoading {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}