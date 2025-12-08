package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.CompoundCompostingRoomModel
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.TB_COMPOUND_COMPOSTING

@Dao
interface CompoundCompostingDao {

    @Insert
    suspend fun insert(model: CompoundCompostingRoomModel)

    @Query("SELECT * FROM $TB_COMPOUND_COMPOSTING")
    suspend fun all(): List<CompoundCompostingRoomModel>

    @Query("SELECT * FROM $TB_COMPOUND_COMPOSTING WHERE status = :status ")
    suspend fun getByStatus(status: Status): CompoundCompostingRoomModel

    @Query("SELECT * FROM $TB_COMPOUND_COMPOSTING WHERE status = :status ")
    suspend fun listByStatus(status: Status): List<CompoundCompostingRoomModel>

}