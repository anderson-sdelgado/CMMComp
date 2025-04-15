package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.AtividadeRoomModel
import br.com.usinasantafe.cmm.utils.TB_ATIVIDADE

@Dao
interface AtividadeDao {

    @Insert
    fun insertAll(list: List<AtividadeRoomModel>)

    @Query("DELETE FROM $TB_ATIVIDADE")
    suspend fun deleteAll()

}