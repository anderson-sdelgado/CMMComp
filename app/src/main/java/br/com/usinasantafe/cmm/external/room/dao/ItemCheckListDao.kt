package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import br.com.usinasantafe.cmm.utils.TB_ITEM_CHECKLIST

@Dao
interface ItemCheckListDao {

    @Insert
    fun insertAll(list: List<ItemCheckListRoomModel>)

    @Query("DELETE FROM $TB_ITEM_CHECKLIST")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM_CHECKLIST")
    suspend fun listAll(): List<ItemCheckListRoomModel>

}
