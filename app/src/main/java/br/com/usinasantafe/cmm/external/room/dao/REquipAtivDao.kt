package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipAtivRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_EQUIP_ATIV

@Dao
interface REquipAtivDao {

    @Insert
    fun insertAll(list: List<REquipAtivRoomModel>)

    @Query("DELETE FROM $TB_R_EQUIP_ATIV")
    suspend fun deleteAll()

}
