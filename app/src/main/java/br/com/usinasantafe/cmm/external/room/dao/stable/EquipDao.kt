package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.TB_EQUIP
import br.com.usinasantafe.cmm.lib.TypeEquip

@Dao
interface EquipDao {

    @Insert
    fun insertAll(list: List<EquipRoomModel>)

    @Update
    fun update(model: EquipRoomModel)

    @Query("DELETE FROM $TB_EQUIP")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_EQUIP")
    suspend fun all(): List<EquipRoomModel>

    @Query("SELECT * FROM $TB_EQUIP WHERE id = :id")
    suspend fun getByIdEquip(id: Int): EquipRoomModel

    @Query("SELECT COUNT(id) FROM $TB_EQUIP WHERE nro = :nro AND typeEquip = :typeEquip")
    suspend fun countByNroEquipAndTypeEquip(
        nro: Long,
        typeEquip: TypeEquip
    ): Int

    @Query("SELECT * FROM $TB_EQUIP WHERE nro = :nro")
    suspend fun getByNroEquip(nro: Long): EquipRoomModel

}