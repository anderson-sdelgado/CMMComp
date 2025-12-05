package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_ITEM_MOTO_MEC

@Dao
interface ItemMotoMecDao {

    @Insert
    suspend fun insert(model: ItemMotoMecRoomModel)

    @Update
    suspend fun update(model: ItemMotoMecRoomModel)

    @Delete
    suspend fun delete(model: ItemMotoMecRoomModel)

    @Query("SELECT * FROM $TB_ITEM_MOTO_MEC")
    suspend fun all(): List<ItemMotoMecRoomModel>

    @Query("SELECT COUNT(id) FROM $TB_ITEM_MOTO_MEC WHERE idHeader = :idHeader")
    suspend fun countByIdHeader(idHeader: Int): Int

    @Query("SELECT * FROM $TB_ITEM_MOTO_MEC WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<ItemMotoMecRoomModel>

    @Query("SELECT * FROM $TB_ITEM_MOTO_MEC WHERE idHeader = :idHeader and statusSend = :statusSend")
    suspend fun listByIdHeaderAndStatusSend(
        idHeader: Int,
        statusSend: StatusSend
    ): List<ItemMotoMecRoomModel>

    @Query("SELECT * FROM $TB_ITEM_MOTO_MEC WHERE id = :id")
    suspend fun getById(id: Int): ItemMotoMecRoomModel

    @Query("SELECT COUNT(id) FROM $TB_ITEM_MOTO_MEC WHERE idStop = :idStop AND idHeader = :idHeader")
    suspend fun countByIdStopAndIdHeader(idStop: Int, idHeader: Int): Int

    @Query("SELECT * FROM $TB_ITEM_MOTO_MEC WHERE idHeader = :idHeader ORDER BY id DESC LIMIT 1")
    suspend fun getLastByIdHeader(idHeader: Int): ItemMotoMecRoomModel?

}