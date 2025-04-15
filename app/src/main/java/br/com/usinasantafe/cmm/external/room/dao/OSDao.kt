package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.utils.TB_OS

@Dao
interface OSDao {

    @Insert
    fun insertAll(list: List<OSRoomModel>)

    @Query("DELETE FROM $TB_OS")
    suspend fun deleteAll()

}
