package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.utils.FunctionItemMenu

interface ItemMenuPMMRepository {
    suspend fun addAll(list: List<ItemMenuPMM>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<ItemMenuPMM>>
    suspend fun listByTypeList(typeList: List<FunctionItemMenu>): Result<List<ItemMenuPMM>>
}