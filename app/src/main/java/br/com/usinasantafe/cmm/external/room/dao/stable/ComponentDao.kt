package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.cmm.lib.TB_COMPONENT

@Dao
interface ComponentDao {

    @Insert
    fun insertAll(list: List<ComponentRoomModel>)

    @Query("DELETE FROM $TB_COMPONENT")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_COMPONENT")
    suspend fun all(): List<ComponentRoomModel>

}