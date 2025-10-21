package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.utils.TB_HEADER_CHECK_LIST

@Dao
interface HeaderCheckListDao {

    @Insert
    suspend fun insert(model: HeaderCheckListRoomModel): Long

    @Query("SELECT * FROM $TB_HEADER_CHECK_LIST")
    suspend fun all(): List<HeaderCheckListRoomModel>

}