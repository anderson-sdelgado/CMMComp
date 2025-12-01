package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_ACTIVITY_STOP
import br.com.usinasantafe.cmm.utils.TB_R_ITEM_MENU_STOP

@Dao
interface RItemMenuStopDao {

    @Insert
    fun insertAll(list: List<RItemMenuStopRoomModel>)

    @Query("DELETE FROM $TB_R_ITEM_MENU_STOP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_ITEM_MENU_STOP")
    suspend fun all(): List<RItemMenuStopRoomModel>

}