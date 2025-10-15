package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan

interface ItemOSMecanRepository {
    suspend fun addAll(list: List<ItemOSMecan>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<ItemOSMecan>>
}