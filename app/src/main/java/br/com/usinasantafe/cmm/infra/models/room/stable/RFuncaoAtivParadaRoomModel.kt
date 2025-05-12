package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
import br.com.usinasantafe.cmm.utils.TB_R_FUNCAO_ATIV_PARADA
import br.com.usinasantafe.cmm.utils.TypeOper

@Entity(tableName = TB_R_FUNCAO_ATIV_PARADA)
data class RFuncaoAtivParadaRoomModel(
    @PrimaryKey
    val idRFuncaoAtivPar: Int,
    val idAtivPar: Int,
    val funcAtiv: Int,
    val funcParada: Int,
    val tipoFuncao: Int
)

fun RFuncaoAtivParadaRoomModel.roomModelToEntity(): RFuncaoAtivParada {
    return RFuncaoAtivParada(
        idRFuncaoAtivPar = this.idRFuncaoAtivPar,
        idAtivPar = this.idAtivPar,
        funcAtiv = FuncAtividade.entries[this.funcAtiv],
        funcParada = FuncParada.entries[this.funcParada],
        tipoFuncao = TypeOper.entries[this.tipoFuncao]
    )
}

fun RFuncaoAtivParada.entityToRoomModel(): RFuncaoAtivParadaRoomModel {
    return RFuncaoAtivParadaRoomModel(
        idRFuncaoAtivPar = this.idRFuncaoAtivPar,
        idAtivPar = this.idAtivPar,
        funcAtiv = this.funcAtiv.ordinal,
        funcParada = this.funcParada.ordinal,
        tipoFuncao = this.tipoFuncao.ordinal
    )
}
