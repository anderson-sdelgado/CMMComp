package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.BocalRoomModel
import br.com.usinasantafe.cmm.utils.TB_BOCAL

@Dao
interface BocalDao {

    @Insert
    fun insertAll(list: List<BocalRoomModel>)

    @Query("DELETE FROM $TB_BOCAL")
    suspend fun deleteAll()

}
