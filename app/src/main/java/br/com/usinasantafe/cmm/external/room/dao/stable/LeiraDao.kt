package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.LeiraRoomModel
import br.com.usinasantafe.cmm.utils.TB_LEIRA

@Dao
interface LeiraDao {

    @Insert
    fun insertAll(list: List<LeiraRoomModel>)

    @Query("DELETE FROM $TB_LEIRA")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_LEIRA")
    suspend fun listAll(): List<LeiraRoomModel>

}
