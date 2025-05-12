package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ParadaRoomModel
import br.com.usinasantafe.cmm.utils.TB_PARADA

@Dao
interface ParadaDao {

    @Insert
    fun insertAll(list: List<ParadaRoomModel>)

    @Query("DELETE FROM $TB_PARADA")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_PARADA")
    suspend fun listAll(): List<ParadaRoomModel>

}
