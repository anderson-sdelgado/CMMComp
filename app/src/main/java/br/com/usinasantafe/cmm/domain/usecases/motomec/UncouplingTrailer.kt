package br.com.usinasantafe.cmm.domain.usecases.motomec

import javax.inject.Inject

interface UncouplingTrailer {
    suspend operator fun invoke(): Result<Boolean>
}

class IUncouplingTrailer @Inject constructor(
): UncouplingTrailer {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}