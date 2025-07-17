package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.utils.OptionRespCheckList
import javax.inject.Inject

interface SetRespItemCheckList {
    suspend operator fun invoke(
        pos: Int,
        id: Int,
        option: OptionRespCheckList
    ): Result<Boolean>
}

class ISetRespItemCheckList @Inject constructor(
): SetRespItemCheckList {

    override suspend fun invoke(
        pos: Int,
        id: Int,
        option: OptionRespCheckList
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}