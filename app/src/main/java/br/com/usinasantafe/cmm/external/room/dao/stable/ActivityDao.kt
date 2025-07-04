package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.utils.TB_ACTIVITY

@Dao
interface ActivityDao {

    @Insert
    fun insertAll(list: List<ActivityRoomModel>)

    @Query("DELETE FROM $TB_ACTIVITY")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ACTIVITY")
    suspend fun listAll(): List<ActivityRoomModel>

    @Query("SELECT * FROM $TB_ACTIVITY WHERE idActivity IN (:ids)")
    suspend fun listByIdList(ids: List<Int>): List<ActivityRoomModel>

}