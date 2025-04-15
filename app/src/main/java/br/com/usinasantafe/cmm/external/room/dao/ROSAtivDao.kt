package br.com.usinasantafe.cmm.external.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSAtivRoomModel
import br.com.usinasantafe.cmm.utils.TB_R_OS_ATIV

@Dao
interface ROSAtivDao {

    @Insert
    fun insertAll(list: List<ROSAtivRoomModel>)

    @Query("DELETE FROM $TB_R_OS_ATIV")
    suspend fun deleteAll()

}
