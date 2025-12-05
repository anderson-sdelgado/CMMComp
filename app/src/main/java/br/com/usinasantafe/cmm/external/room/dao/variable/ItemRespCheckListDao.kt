package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.lib.TB_ITEM_RESP_CHECK_LIST

@Dao
interface ItemRespCheckListDao {

    @Insert
    suspend fun insert(itemRespCheckListRoomModel: ItemRespCheckListRoomModel)

    @Update
    suspend fun update(itemRespCheckListRoomModel: ItemRespCheckListRoomModel)

    @Delete
    suspend fun delete(itemRespCheckListRoomModel: ItemRespCheckListRoomModel)

    @Query("SELECT * FROM $TB_ITEM_RESP_CHECK_LIST")
    suspend fun all(): List<ItemRespCheckListRoomModel>

    @Query("SELECT * FROM $TB_ITEM_RESP_CHECK_LIST WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<ItemRespCheckListRoomModel>

    @Query("SELECT * FROM $TB_ITEM_RESP_CHECK_LIST WHERE id = :id")
    suspend fun getById(id: Int): ItemRespCheckListRoomModel

}