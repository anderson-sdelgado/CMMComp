package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.ImplementRoomModel
import br.com.usinasantafe.cmm.lib.TB_IMPLEMENT

@Dao
interface ImplementDao {

    @Insert
    suspend fun insert(model: ImplementRoomModel)

    @Query("SELECT * FROM $TB_IMPLEMENT")
    suspend fun all(): List<ImplementRoomModel>

}