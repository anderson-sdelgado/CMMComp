package br.com.usinasantafe.cmm.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.cmm.infra.models.room.variable.MechanicRoomModel
import br.com.usinasantafe.cmm.lib.TB_MECHANIC

@Dao
interface MechanicDao {

    @Insert
    suspend fun insert(model: MechanicRoomModel)

    @Update
    suspend fun update(model: MechanicRoomModel)

    @Query("SELECT * FROM $TB_MECHANIC")
    suspend fun all(): List<MechanicRoomModel>

    @Query("SELECT COUNT(id) FROM $TB_MECHANIC WHERE idHeader = :idHeader and dateHourFinish is null")
    suspend fun countOpenByIdHeader(idHeader: Int): Int

    @Query("SELECT * FROM $TB_MECHANIC WHERE dateHourFinish is null")
    suspend fun getByOpen(): MechanicRoomModel

}