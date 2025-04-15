package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnoRoomModel
import br.com.usinasantafe.cmm.utils.TB_TURNO

@Dao
interface TurnoDao {

    @Insert
    fun insertAll(list: List<TurnoRoomModel>)

    @Query("DELETE FROM $TB_TURNO")
    suspend fun deleteAll()

}
