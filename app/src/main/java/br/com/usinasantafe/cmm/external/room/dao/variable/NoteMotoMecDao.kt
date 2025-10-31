package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_NOTE_MOTO_MEC

@Dao
interface NoteMotoMecDao {

    @Insert
    suspend fun insert(model: NoteMotoMecRoomModel)

    @Update
    suspend fun update(model: NoteMotoMecRoomModel)

    @Delete
    suspend fun delete(model: NoteMotoMecRoomModel)

    @Query("SELECT * FROM $TB_NOTE_MOTO_MEC")
    suspend fun all(): List<NoteMotoMecRoomModel>

    @Query("SELECT COUNT(id) FROM $TB_NOTE_MOTO_MEC WHERE idHeader = :idHeader")
    suspend fun countByIdHeader(idHeader: Int): Int

    @Query("SELECT * FROM $TB_NOTE_MOTO_MEC WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<NoteMotoMecRoomModel>

    @Query("SELECT * FROM $TB_NOTE_MOTO_MEC WHERE idHeader = :idHeader and statusSend = :statusSend")
    suspend fun listByIdHeaderAndStatusSend(
        idHeader: Int,
        statusSend: StatusSend
    ): List<NoteMotoMecRoomModel>

    @Query("SELECT * FROM $TB_NOTE_MOTO_MEC WHERE id = :id")
    suspend fun getById(id: Int): NoteMotoMecRoomModel

    @Query("SELECT COUNT(id) FROM $TB_NOTE_MOTO_MEC WHERE idStop = :idStop AND idHeader = :idHeader")
    suspend fun countByIdStopAndIdHeader(idStop: Int, idHeader: Int): Int

}