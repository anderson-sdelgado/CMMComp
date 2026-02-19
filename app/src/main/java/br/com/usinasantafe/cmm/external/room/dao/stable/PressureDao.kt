package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.PressureRoomModel
import br.com.usinasantafe.cmm.lib.TB_PRESSURE

@Dao
interface PressureDao {

    @Insert
    fun insertAll(list: List<PressureRoomModel>)

    @Query("DELETE FROM $TB_PRESSURE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_PRESSURE")
    suspend fun all(): List<PressureRoomModel>

    @Query("SELECT * FROM $TB_PRESSURE WHERE idNozzle = :idNozzle")
    suspend fun listByIdNozzle(idNozzle: Int): List<PressureRoomModel>

    @Query("SELECT * FROM $TB_PRESSURE WHERE idNozzle = :idNozzle AND value = :valuePressure")
    suspend fun listByIdNozzleAndValuePressure(idNozzle: Int, valuePressure: Double): List<PressureRoomModel>

}