package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.TB_HEADER_MOTO_MEC

@Dao
interface HeaderMotoMecDao {

    @Insert
    fun insert(model: HeaderMotoMecRoomModel)

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC")
    fun listAll(): List<HeaderMotoMecRoomModel>

    @Query("SELECT * FROM $TB_HEADER_MOTO_MEC WHERE status = :status")
    fun listByStatus(status: Status): List<HeaderMotoMecRoomModel>
}