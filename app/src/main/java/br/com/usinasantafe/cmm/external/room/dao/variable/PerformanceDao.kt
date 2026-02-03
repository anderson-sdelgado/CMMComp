package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.lib.TB_PERFORMANCE

@Dao
interface PerformanceDao {

    @Insert
    suspend fun insert(model: PerformanceRoomModel)

    @Query("SELECT * FROM $TB_PERFORMANCE")
    suspend fun all(): List<PerformanceRoomModel>

    @Query("SELECT EXISTS(SELECT 1 FROM $TB_PERFORMANCE WHERE idHeader = :idHeader AND nroOS = :nroOS)")
    suspend fun hasByIdHeaderAndNroOS(idHeader: Int, nroOS: Int): Boolean

    @Query("SELECT * FROM $TB_PERFORMANCE WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<PerformanceRoomModel>

}