package br.com.usinasantafe.cmm.domain.usecases.motomec

import javax.inject.Inject

interface CheckCouplingTrailer {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckCouplingTrailer @Inject constructor(
): CheckCouplingTrailer {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}