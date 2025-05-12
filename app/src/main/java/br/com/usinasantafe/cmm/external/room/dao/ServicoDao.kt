package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ServicoRoomModel
import br.com.usinasantafe.cmm.utils.TB_SERVICO

@Dao
interface ServicoDao {

    @Insert
    fun insertAll(list: List<ServicoRoomModel>)

    @Query("DELETE FROM $TB_SERVICO")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_SERVICO")
    suspend fun listAll(): List<ServicoRoomModel>

}
