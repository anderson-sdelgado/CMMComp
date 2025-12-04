package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.lib.TB_R_EQUIP_ACTIVITY

@Dao
interface REquipActivityDao {

    @Insert
    fun insertAll(list: List<REquipActivityRoomModel>)

    @Query("DELETE FROM $TB_R_EQUIP_ACTIVITY")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_EQUIP_ACTIVITY")
    suspend fun all(): List<REquipActivityRoomModel>

    @Query("SELECT * FROM $TB_R_EQUIP_ACTIVITY WHERE idEquip = :idEquip")
    suspend fun listByIdEquip(idEquip: Int): List<REquipActivityRoomModel>

}
