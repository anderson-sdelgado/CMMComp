package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.InputCompostingRoomModel
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusComposting
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_INPUT_COMPOSTING

@Dao
interface InputCompostingDao {

    @Insert
    suspend fun insert(model: InputCompostingRoomModel)

    @Query("SELECT * FROM $TB_INPUT_COMPOSTING")
    suspend fun all(): List<InputCompostingRoomModel>

    @Query("SELECT * FROM $TB_INPUT_COMPOSTING WHERE status = :status AND statusSend = :statusSend AND statusComposting = :statusComposting")
    suspend fun listByStatusAndStatusSendAndStatusComposting(
        status: Status,
        statusSend: StatusSend,
        statusComposting: StatusComposting
    ): List<InputCompostingRoomModel>

    @Query("SELECT * FROM $TB_INPUT_COMPOSTING WHERE status = :status ")
    suspend fun getByStatus(status: Status): InputCompostingRoomModel

    @Query("SELECT * FROM $TB_INPUT_COMPOSTING WHERE status = :status")
    suspend fun listByStatus(
        status: Status,
    ): List<InputCompostingRoomModel>

}