package br.com.usinasantafe.cmm.domain.usecases.motomec

import javax.inject.Inject

interface SetNoteMotoMec {
    suspend operator fun invoke(item: Pair<Int, String>): Result<Boolean>
}

class ISetNoteMotoMec @Inject constructor(
): SetNoteMotoMec {

    override suspend fun invoke(item: Pair<Int, String>): Result<Boolean> {
        TODO("Not yet implemented")
    }

}