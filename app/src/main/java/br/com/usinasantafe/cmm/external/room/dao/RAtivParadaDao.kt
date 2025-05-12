package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.RAtivParadaRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_ATIV_PARADA

@Dao
interface RAtivParadaDao {

    @Insert
    fun insertAll(list: List<RAtivParadaRoomModel>)

    @Query("DELETE FROM $TB_R_ATIV_PARADA")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_ATIV_PARADA")
    suspend fun listAll(): List<RAtivParadaRoomModel>

}
