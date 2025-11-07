package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.utils.TB_ITEM_MENU

@Dao
interface ItemMenuDao {

    @Insert
    suspend fun insertAll(list: List<ItemMenuRoomModel>)

    @Query("DELETE FROM $TB_ITEM_MENU")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM_MENU")
    suspend fun all(): List<ItemMenuRoomModel>

    @Query("SELECT * FROM $TB_ITEM_MENU WHERE idType in (:idType)")
    suspend fun listByIdTypeList(idType: List<Int>): List<ItemMenuRoomModel>
}