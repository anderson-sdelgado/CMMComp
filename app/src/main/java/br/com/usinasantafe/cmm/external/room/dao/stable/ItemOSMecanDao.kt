package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMecanRoomModel
import br.com.usinasantafe.cmm.utils.TB_ITEM_OS_MECAN

@Dao
interface ItemOSMecanDao {

    @Insert
    fun insertAll(list: List<ItemOSMecanRoomModel>)

    @Query("DELETE FROM $TB_ITEM_OS_MECAN")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM_OS_MECAN")
    suspend fun listAll(): List<ItemOSMecanRoomModel>

}
