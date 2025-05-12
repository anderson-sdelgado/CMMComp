package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.PropriedadeRoomModel
import br.com.usinasantafe.cmm.utils.TB_PROPRIEDADE

@Dao
interface PropriedadeDao {

    @Insert
    fun insertAll(list: List<PropriedadeRoomModel>)

    @Query("DELETE FROM $TB_PROPRIEDADE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_PROPRIEDADE")
    suspend fun listAll(): List<PropriedadeRoomModel>

}
