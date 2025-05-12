package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.FrenteRoomModel
import br.com.usinasantafe.cmm.utils.TB_FRENTE

@Dao
interface FrenteDao {

    @Insert
    fun insertAll(list: List<FrenteRoomModel>)

    @Query("DELETE FROM $TB_FRENTE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_FRENTE")
    suspend fun listAll(): List<FrenteRoomModel>

}
