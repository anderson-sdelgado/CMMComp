package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.utils.TB_OS

@Dao
interface OSDao {

    @Insert
    suspend fun insertAll(list: List<OSRoomModel>)

    @Insert
    suspend fun insert(model: OSRoomModel)

    @Query("DELETE FROM $TB_OS")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_OS")
    suspend fun listAll(): List<OSRoomModel>

    @Query("SELECT count(*) FROM $TB_OS WHERE nroOS = :nroOS")
    suspend fun checkNroOS(nroOS: Int): Int

    @Query("SELECT * FROM $TB_OS WHERE nroOS = :nroOS")
    suspend fun listByNroOS(nroOS: Int): List<OSRoomModel>
}
