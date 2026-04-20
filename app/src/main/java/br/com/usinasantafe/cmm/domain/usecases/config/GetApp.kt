package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.lib.App
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetApp {
    suspend operator fun invoke(): Result<App>
}

class IGetApp @Inject constructor(
): GetApp {

    override suspend fun invoke(): Result<App> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}