package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.utils.TB_FUNCTION_STOP

@Dao
interface FunctionStopDao {

    @Insert
    suspend fun insertAll(list: List<FunctionStopRoomModel>)

    @Query("DELETE FROM $TB_FUNCTION_STOP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_FUNCTION_STOP")
    suspend fun all(): List<FunctionStopRoomModel>

}