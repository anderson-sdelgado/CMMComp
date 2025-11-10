package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.StatusTranshipment
import javax.inject.Inject

interface GetStatusTranshipment {
    suspend operator fun invoke(): Result<StatusTranshipment>
}

class IGetStatusTranshipment @Inject constructor(
): GetStatusTranshipment {

    override suspend fun invoke(): Result<StatusTranshipment> {
        TODO("Not yet implemented")
    }

}