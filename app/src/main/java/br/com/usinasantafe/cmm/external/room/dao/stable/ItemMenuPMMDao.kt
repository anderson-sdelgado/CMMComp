package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuPMMRoomModel
import br.com.usinasantafe.cmm.utils.TB_ITEM_MENU_PMM
import br.com.usinasantafe.cmm.utils.FunctionItemMenu

@Dao
interface ItemMenuPMMDao {

    @Insert
    suspend fun insertAll(list: List<ItemMenuPMMRoomModel>)

    @Query("DELETE FROM $TB_ITEM_MENU_PMM")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM_MENU_PMM")
    suspend fun all(): List<ItemMenuPMMRoomModel>

    @Query("SELECT * FROM $TB_ITEM_MENU_PMM WHERE type IN (:typeList)")
    suspend fun listByTypeList(typeList: List<FunctionItemMenu>): List<ItemMenuPMMRoomModel>

}