package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.utils.TB_COLAB

@Dao
interface ColabDao {

    @Insert
    fun insertAll(list: List<ColabRoomModel>)

    @Query("DELETE FROM $TB_COLAB")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_COLAB")
    suspend fun listAll(): List<ColabRoomModel>

    @Query("SELECT count(*) FROM $TB_COLAB WHERE regColab = :reg")
    suspend fun checkReg(reg: Int): Int

}
