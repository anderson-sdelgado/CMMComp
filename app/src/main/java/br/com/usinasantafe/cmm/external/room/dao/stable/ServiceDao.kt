package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.cmm.lib.TB_SERVICE

@Dao
interface ServiceDao {

    @Insert
    fun insertAll(list: List<ServiceRoomModel>)

    @Query("DELETE FROM $TB_SERVICE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_SERVICE")
    suspend fun all(): List<ServiceRoomModel>

}