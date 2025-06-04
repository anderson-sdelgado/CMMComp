package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.TB_NOTE_MOTO_MEC

@Dao
interface NoteMotoMecDao {
    @Insert
    fun insert(model: NoteMotoMecRoomModel)

    @Query("SELECT * FROM $TB_NOTE_MOTO_MEC")
    fun listAll(): List<NoteMotoMecRoomModel>

}