package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_OS_ACTIVITY

@Dao
interface ROSActivityDao {

    @Insert
    fun insertAll(list: List<ROSActivityRoomModel>)

    @Query("DELETE FROM $TB_R_OS_ACTIVITY")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_OS_ACTIVITY")
    suspend fun listAll(): List<ROSActivityRoomModel>

}
