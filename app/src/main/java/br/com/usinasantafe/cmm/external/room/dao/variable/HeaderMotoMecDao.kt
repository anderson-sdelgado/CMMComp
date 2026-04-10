package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_HEADER_MOTO_MEC

@Dao
interface HeaderMotoMecDao {

    @Insert
    suspend fun insert(model: HeaderMotoMecRoomModel): Long

    @Update
    suspend fun update(model: HeaderMotoMecRoomModel)

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC")
    suspend fun all(): List<HeaderMotoMecRoomModel>

    @Query("SELECT EXISTS(SELECT 1 FROM $TB_HEADER_MOTO_MEC WHERE status <> :status)")
    suspend fun hasByDiffStatus(status: Status): Boolean

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE statusSend = :statusSend")
    suspend fun listByStatusSend(statusSend: StatusSend): List<HeaderMotoMecRoomModel>

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE id = :id")
    suspend fun getById(id: Int): HeaderMotoMecRoomModel

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE idEquip = :idEquip AND status = :status")
    suspend fun getByIdEquipAndOpen(idEquip: Int, status: Status = Status.OPEN): HeaderMotoMecRoomModel

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE idHeader = :idHeader AND status = :status")
    suspend fun listByIdHeader(idHeader: Int, status: Status = Status.CLOSE): List<HeaderMotoMecRoomModel>

    @Query("UPDATE $TB_HEADER_MOTO_MEC SET status = :statusClose WHERE status = :statusFinish")
    suspend fun updateOpenToClose(statusClose: Status = Status.CLOSE, statusFinish: Status = Status.OPEN)

    @Query("UPDATE $TB_HEADER_MOTO_MEC SET status = :statusOpen WHERE idEquip = :idEquip and status = :statusClose")
    suspend fun updateStatusOpenByIdEquip(idEquip: Int, statusOpen: Status = Status.OPEN, statusClose: Status = Status.CLOSE)

    @Query("UPDATE $TB_HEADER_MOTO_MEC SET status = :statusOpen WHERE id = :id")
    suspend fun updateStatusOpenById(id: Int, statusOpen: Status = Status.OPEN)

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE status = :status")
    suspend fun getByStatusOpen(status: Status = Status.OPEN): HeaderMotoMecRoomModel

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE idEquip = :idEquip AND status <> :status")
    suspend fun getByIdEquipAndNotFinish(idEquip: Int, status: Status = Status.FINISH): HeaderMotoMecRoomModel

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE status <> :status")
    suspend fun listByNotFinish(status: Status = Status.FINISH): List<HeaderMotoMecRoomModel>

}