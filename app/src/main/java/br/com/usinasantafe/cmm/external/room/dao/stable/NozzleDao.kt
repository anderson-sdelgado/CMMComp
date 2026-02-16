package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.NozzleRoomModel
import br.com.usinasantafe.cmm.lib.TB_NOZZLE

@Dao
interface NozzleDao {

    @Insert
    fun insertAll(list: List<NozzleRoomModel>)

    @Query("DELETE FROM $TB_NOZZLE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_NOZZLE")
    suspend fun all(): List<NozzleRoomModel>

    @Query("SELECT * FROM $TB_NOZZLE ORDER BY cod ASC")
    suspend fun allOrderByCod(): List<NozzleRoomModel>

}