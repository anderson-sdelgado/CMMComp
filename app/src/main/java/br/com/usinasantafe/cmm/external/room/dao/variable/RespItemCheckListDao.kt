package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.RespItemCheckListRoomModel
import br.com.usinasantafe.cmm.lib.TB_RESP_ITEM_CHECK_LIST

@Dao
interface RespItemCheckListDao {

    @Insert
    suspend fun insert(respItemCheckListRoomModel: RespItemCheckListRoomModel)

    @Update
    suspend fun update(respItemCheckListRoomModel: RespItemCheckListRoomModel)

    @Delete
    suspend fun delete(respItemCheckListRoomModel: RespItemCheckListRoomModel)

    @Query("SELECT * FROM $TB_RESP_ITEM_CHECK_LIST")
    suspend fun all(): List<RespItemCheckListRoomModel>

    @Query("SELECT * FROM $TB_RESP_ITEM_CHECK_LIST WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<RespItemCheckListRoomModel>

    @Query("SELECT * FROM $TB_RESP_ITEM_CHECK_LIST WHERE id = :id")
    suspend fun getById(id: Int): RespItemCheckListRoomModel


}