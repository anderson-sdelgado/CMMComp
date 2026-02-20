package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
import br.com.usinasantafe.cmm.lib.TB_COLLECTION

@Dao
interface CollectionDao {

    @Insert
    suspend fun insert(model: CollectionRoomModel)

    @Update
    suspend fun update(model: CollectionRoomModel)

    @Query("SELECT * FROM $TB_COLLECTION")
    suspend fun all(): List<CollectionRoomModel>

    @Query("SELECT * FROM $TB_COLLECTION WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<CollectionRoomModel>

    @Query("SELECT EXISTS(SELECT * FROM $TB_COLLECTION WHERE idHeader = :idHeader AND nroOS = :nroOS)")
    suspend fun hasByIdHeaderAndNroOS(idHeader: Int, nroOS: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM $TB_COLLECTION WHERE idHeader = :idHeader AND value is null)")
    suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Boolean

    @Query("SELECT * FROM $TB_COLLECTION WHERE id = :id")
    suspend fun getById(id: Int): CollectionRoomModel

}