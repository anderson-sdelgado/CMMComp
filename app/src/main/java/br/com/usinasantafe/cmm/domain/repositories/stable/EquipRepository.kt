package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface EquipRepository : UpdateRepository<Equip> {
    suspend fun saveEquipMain(entity: Equip): EmptyResult
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