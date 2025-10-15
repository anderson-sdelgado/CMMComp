package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_ACTIVITY_STOP

@Dao
interface RActivityStopDao {

    @Insert
    fun insertAll(list: List<RActivityStopRoomModel>)

    @Query("DELETE FROM $TB_R_ACTIVITY_STOP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_ACTIVITY_STOP")
    suspend fun all(): List<RActivityStopRoomModel>

    @Query("SELECT * FROM $TB_R_ACTIVITY_STOP WHERE idActivity = :idActivity")
    suspend fun listByIdActivity(idActivity: Int): List<RActivityStopRoomModel>

}
