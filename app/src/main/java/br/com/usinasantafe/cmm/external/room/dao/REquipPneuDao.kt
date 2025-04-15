package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipPneuRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_EQUIP_PNEU

@Dao
interface REquipPneuDao {

    @Insert
    fun insertAll(list: List<REquipPneuRoomModel>)

    @Query("DELETE FROM $TB_R_EQUIP_PNEU")
    suspend fun deleteAll()

}
