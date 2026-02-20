package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Collection
import br.com.usinasantafe.cmm.utils.EmptyResult

interface FertigationRepository {
    suspend fun setIdEquipMotorPump(idEquip: Int): EmptyResult
    suspend fun setIdNozzle(id: Int): EmptyResult
    suspend fun getIdNozzle(): Result<Int>
    suspend fun setValuePressure(value: Double): EmptyResult
    suspend fun getValuePressure(): Result<Double>
    suspend fun setSpeedPressure(speed: Int): EmptyResult
    suspend fun initialCollection(nroOS: Int, idHeader: Int): Result<Unit>
    suspend fun hasCollectionByIdHeaderAndValueNull(idHeader: Int): Result<Boolean>
    suspend fun listCollectionByIdHeader(idHeader: Int): Result<List<Collection>>
    suspend fun updateCollection(id: Int, value: Double): EmptyResult
}