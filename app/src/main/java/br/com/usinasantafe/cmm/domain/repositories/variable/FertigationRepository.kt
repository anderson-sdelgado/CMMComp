package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.utils.EmptyResult

interface FertigationRepository {
    suspend fun setIdEquipMotorPump(idEquip: Int): EmptyResult
    suspend fun setIdNozzle(id: Int): EmptyResult
    suspend fun getIdNozzle(): Result<Int>
}