package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.utils.EmptyResult

interface PressureRepository {
    suspend fun addAll(list: List<Pressure>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Pressure>>
    suspend fun listByIdNozzle(idNozzle: Int): Result<List<Pressure>>
    suspend fun listByIdNozzleAndValuePressure(
        idNozzle: Int,
        valuePressure: Double
    ): Result<List<Pressure>>

}