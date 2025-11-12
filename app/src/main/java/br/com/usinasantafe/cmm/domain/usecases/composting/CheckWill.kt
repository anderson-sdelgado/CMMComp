package br.com.usinasantafe.cmm.domain.usecases.composting

import javax.inject.Inject

interface CheckWill {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckWill @Inject constructor(
): CheckWill {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}