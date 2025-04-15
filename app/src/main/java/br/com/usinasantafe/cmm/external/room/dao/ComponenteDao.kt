package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponenteRoomModel
import br.com.usinasantafe.cmm.utils.TB_COMPONENTE

@Dao
interface ComponenteDao {

    @Insert
    fun insertAll(list: List<ComponenteRoomModel>)

    @Query("DELETE FROM $TB_COMPONENTE")
    suspend fun deleteAll()

}
