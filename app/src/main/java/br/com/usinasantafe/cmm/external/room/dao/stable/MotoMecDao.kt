package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.MotoMecRoomModel
import br.com.usinasantafe.cmm.utils.TB_MOTOMEC

@Dao
interface MotoMecDao {

    @Insert
    fun insertAll(list: List<MotoMecRoomModel>)

    @Query("DELETE FROM $TB_MOTOMEC")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_MOTOMEC")
    suspend fun listAll(): List<MotoMecRoomModel>

}
