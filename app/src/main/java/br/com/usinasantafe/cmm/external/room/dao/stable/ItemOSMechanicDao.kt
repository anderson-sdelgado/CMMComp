package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMechanicRoomModel
import br.com.usinasantafe.cmm.lib.TB_ITEM_OS_MECHANIC

@Dao
interface ItemOSMechanicDao {

    @Insert
    fun insertAll(list: List<ItemOSMechanicRoomModel>)

    @Query("DELETE FROM $TB_ITEM_OS_MECHANIC")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM_OS_MECHANIC")
    suspend fun all(): List<ItemOSMechanicRoomModel>

}