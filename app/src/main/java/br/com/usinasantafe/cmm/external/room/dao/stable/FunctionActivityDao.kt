package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.utils.TB_FUNCTION_ACTIVITY

@Dao
interface FunctionActivityDao {

    @Insert
    suspend fun insertAll(list: List<FunctionActivityRoomModel>)

    @Query("DELETE FROM $TB_FUNCTION_ACTIVITY")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_FUNCTION_ACTIVITY")
    suspend fun all(): List<FunctionActivityRoomModel>

    @Query("SELECT * FROM $TB_FUNCTION_ACTIVITY WHERE idActivity = :idActivity")
    suspend fun listByIdActivity(idActivity: Int): List<FunctionActivityRoomModel>

}
