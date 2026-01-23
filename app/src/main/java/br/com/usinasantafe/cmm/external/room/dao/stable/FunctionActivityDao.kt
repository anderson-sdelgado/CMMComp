package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.lib.TB_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.lib.TB_PERFORMANCE
import br.com.usinasantafe.cmm.lib.TypeActivity

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

    @Query("SELECT EXISTS(SELECT 1 FROM $TB_FUNCTION_ACTIVITY WHERE idActivity = :idActivity AND typeActivity = :typeActivity)")
    suspend fun hasByIdAndType(idActivity: Int, typeActivity: TypeActivity): Boolean

}
