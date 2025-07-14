package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.RFuncaoAtivParadaRoomModel
import br.com.usinasantafe.cmm.utils.TB_FUNCTION_ACTIVITY_STOP

@Dao
interface RFuncaoAtivParadaDao {

    @Insert
    fun insertAll(list: List<RFuncaoAtivParadaRoomModel>)

    @Query("DELETE FROM $TB_FUNCTION_ACTIVITY_STOP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_FUNCTION_ACTIVITY_STOP")
    suspend fun listAll(): List<RFuncaoAtivParadaRoomModel>

}
