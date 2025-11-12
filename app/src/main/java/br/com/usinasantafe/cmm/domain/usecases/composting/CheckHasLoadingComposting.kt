package br.com.usinasantafe.cmm.domain.usecases.composting

import javax.inject.Inject

interface CheckHasLoadingComposting {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHasLoadingComposting @Inject constructor(
): CheckHasLoadingComposting {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}