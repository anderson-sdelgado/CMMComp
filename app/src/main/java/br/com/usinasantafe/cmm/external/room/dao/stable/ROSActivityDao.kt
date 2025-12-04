package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import br.com.usinasantafe.cmm.lib.TB_R_OS_ACTIVITY

@Dao
interface ROSActivityDao {

    @Insert
    fun insertAll(list: List<ROSActivityRoomModel>)

    @Query("DELETE FROM $TB_R_OS_ACTIVITY")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_OS_ACTIVITY")
    suspend fun listAll(): List<ROSActivityRoomModel>

    @Query("SELECT * FROM $TB_R_OS_ACTIVITY WHERE idOS = :idOS")
    suspend fun listByIdOS(idOS: Int): List<ROSActivityRoomModel>
}
