package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.utils.TB_EQUIP

@Dao
interface EquipDao {

    @Insert
    fun insertAll(list: List<EquipRoomModel>)

    @Update
    fun update(model: EquipRoomModel)

    @Query("DELETE FROM $TB_EQUIP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_EQUIP")
    suspend fun listAll(): List<EquipRoomModel>

    @Query("SELECT * FROM $TB_EQUIP WHERE idEquip = :id")
    suspend fun getByIdEquip(id: Int): EquipRoomModel

}