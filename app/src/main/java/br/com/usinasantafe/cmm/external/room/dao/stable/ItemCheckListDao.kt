package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import br.com.usinasantafe.cmm.lib.TB_ITEM_CHECK_LIST

@Dao
interface ItemCheckListDao {

    @Insert
    fun insertAll(list: List<ItemCheckListRoomModel>)

    @Query("DELETE FROM $TB_ITEM_CHECK_LIST")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM_CHECK_LIST")
    suspend fun all(): List<ItemCheckListRoomModel>

    @Query("SELECT * FROM $TB_ITEM_CHECK_LIST WHERE idCheckList = :idCheckList")
    suspend fun listByIdCheckList(idCheckList: Int): List<ItemCheckListRoomModel>

    @Query("SELECT COUNT(idItemCheckList) FROM $TB_ITEM_CHECK_LIST WHERE idCheckList = :idCheckList")
    suspend fun countByIdCheckList(idCheckList: Int): Int

}
