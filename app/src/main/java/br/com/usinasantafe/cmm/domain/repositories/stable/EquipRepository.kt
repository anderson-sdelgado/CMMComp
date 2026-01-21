package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip

interface EquipRepository {
    suspend fun saveEquipMain(entity: Equip): Result<Boolean>
    suspend fun addAll(list: List<Equip>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(
        token: String
    ): Result<List<Equip>>
    suspend fun getIdEquipMain(): Result<Int>
    suspend fun getNroEquipMain(): Result<Long>
    suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String>
    suspend fun getCodTurnEquip(): Result<Int>
    suspend fun getHourMeter(
    ): Result<Double>
    suspend fun updateHourMeter(
        hourMeter: Double
    ): EmptyResult
    suspend fun getTypeEquipMain(): Result<TypeEquip>
    suspend fun getIdCheckList(): Result<Int>
    suspend fun getFlagMechanic(): Result<Boolean>
    suspend fun getFlagTire(): Result<Boolean>
    suspend fun hasEquipSecondary(
        nroEquip: Long,
        typeEquip: TypeEquip
    ): Result<Boolean>
    suspend fun getIdByNro(nroEquip: Long): Result<Int>
}