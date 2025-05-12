package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.utils.TB_TURN

@Dao
interface TurnDao {

    @Insert
    fun insertAll(list: List<TurnRoomModel>)

    @Query("DELETE FROM $TB_TURN")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_TURN")
    suspend fun listAll(): List<TurnRoomModel>

    @Query("SELECT * FROM $TB_TURN WHERE codTurnEquip = :codTurnEquip")
    suspend fun getListByCodTurnEquip(codTurnEquip: Int): List<TurnRoomModel>

}
