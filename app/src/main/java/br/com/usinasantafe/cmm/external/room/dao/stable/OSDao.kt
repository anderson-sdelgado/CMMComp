package br.com.usinasantafe.cmm.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.lib.TB_OS

@Dao
interface OSDao {

    @Insert
    suspend fun insertAll(list: List<OSRoomModel>)

    @Insert
    suspend fun insert(model: OSRoomModel)

    @Query("DELETE FROM $TB_OS")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_OS")
    suspend fun all(): List<OSRoomModel>

    @Query("SELECT EXISTS(SELECT 1 FROM $TB_OS WHERE nro = :nro)")
    suspend fun hasNro(nro: Int): Boolean

    @Query("SELECT * FROM $TB_OS WHERE nro = :nro")
    suspend fun getByNro(nro: Int): OSRoomModel

    @Query("SELECT EXISTS(SELECT 1 FROM $TB_OS WHERE nro = :nro AND idRelease = :idRelease)")
    suspend fun hasNroAndIdRelease(nro: Int, idRelease: Int): Boolean

}
