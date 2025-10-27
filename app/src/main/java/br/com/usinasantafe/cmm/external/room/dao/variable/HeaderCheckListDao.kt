package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_HEADER_CHECK_LIST

@Dao
interface HeaderCheckListDao {

    @Insert
    suspend fun insert(model: HeaderCheckListRoomModel): Long

    @Update
    suspend fun update(model: HeaderCheckListRoomModel)

    @Query("SELECT * FROM $TB_HEADER_CHECK_LIST")
    suspend fun all(): List<HeaderCheckListRoomModel>

    @Query("SELECT COUNT(id) FROM $TB_HEADER_CHECK_LIST WHERE statusSend = :statusSend")
    suspend fun countByStatusSend(statusSend: StatusSend): Int

    @Query("SELECT * FROM $TB_HEADER_CHECK_LIST WHERE statusSend = :statusSend")
    suspend fun listByStatusSend(statusSend: StatusSend): List<HeaderCheckListRoomModel>


    @Query("SELECT * FROM $TB_HEADER_CHECK_LIST WHERE id = :id")
    suspend fun getById(id: Int): HeaderCheckListRoomModel

}