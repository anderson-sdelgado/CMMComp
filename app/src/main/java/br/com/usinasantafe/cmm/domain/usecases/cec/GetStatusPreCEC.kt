package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.utils.StatusPreCEC
import javax.inject.Inject

interface GetStatusPreCEC {
    suspend operator fun invoke(): Result<StatusPreCEC>
}

class IGetStatusPreCEC @Inject constructor(
): GetStatusPreCEC {

    override suspend fun invoke(): Result<StatusPreCEC> {
        TODO("Not yet implemented")
    }

}