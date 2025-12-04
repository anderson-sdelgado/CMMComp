package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
import br.com.usinasantafe.cmm.lib.TB_NOTE_MECHANIC

@Dao
interface NoteMechanicDao {

    @Insert
    suspend fun insert(model: NoteMechanicRoomModel)

    @Update
    suspend fun update(model: NoteMechanicRoomModel)

    @Query("SELECT * FROM $TB_NOTE_MECHANIC")
    suspend fun all(): List<NoteMechanicRoomModel>

    @Query("SELECT COUNT(id) FROM $TB_NOTE_MECHANIC WHERE idHeader = :idHeader and dateHourFinish is null")
    suspend fun countOpenByIdHeader(idHeader: Int): Int

    @Query("SELECT * FROM $TB_NOTE_MECHANIC WHERE dateHourFinish is null")
    suspend fun getByOpen(): NoteMechanicRoomModel

}