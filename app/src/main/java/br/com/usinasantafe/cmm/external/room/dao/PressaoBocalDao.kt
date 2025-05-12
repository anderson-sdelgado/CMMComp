package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.PressaoBocalRoomModel
import br.com.usinasantafe.cmm.utils.TB_PRESSAO_BOCAL

@Dao
interface PressaoBocalDao {

    @Insert
    fun insertAll(list: List<PressaoBocalRoomModel>)

    @Query("DELETE FROM $TB_PRESSAO_BOCAL")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_PRESSAO_BOCAL")
    suspend fun listAll(): List<PressaoBocalRoomModel>

}
