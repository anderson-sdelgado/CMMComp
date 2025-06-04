package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.utils.TB_STOP

@Dao
interface StopDao {

    @Insert
    fun insertAll(list: List<StopRoomModel>)

    @Query("DELETE FROM $TB_STOP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_STOP")
    suspend fun listAll(): List<StopRoomModel>

}
