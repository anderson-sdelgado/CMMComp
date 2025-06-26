package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_HEADER_MOTO_MEC

@Dao
interface HeaderMotoMecDao {

    @Insert
    suspend fun insert(model: HeaderMotoMecRoomModel)

    @Update
    suspend fun update(model: HeaderMotoMecRoomModel)

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC")
    suspend fun listAll(): List<HeaderMotoMecRoomModel>

    @Query("SELECT COUNT(id) FROM $TB_HEADER_MOTO_MEC WHERE status = :status")
    suspend fun countByStatus(status: Status): Int

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE statusSend = :statusSend")
    suspend fun listByStatusSend(statusSend: StatusSend): List<HeaderMotoMecRoomModel>

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE id = :id")
    suspend fun get(id: Int): HeaderMotoMecRoomModel

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE status = :status")
    suspend fun getByStatus(status: Status): HeaderMotoMecRoomModel

}