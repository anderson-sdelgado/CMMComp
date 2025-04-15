package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Equip // Import da entidade de domínio Equip
import br.com.usinasantafe.cmm.utils.TB_EQUIP // Import da constante do nome da tabela

@Entity(tableName = TB_EQUIP) // Define como entidade do Room e especifica o nome da tabela
data class EquipRoomModel (
    @PrimaryKey // Define nroEquip como chave primária
    val nroEquip: Long,
    val idEquip: Int, // ID interno do Equipamento
    val codClasse: Int, // Código da classe do equipamento
    val descrClasse: String, // Descrição da classe do equipamento
    val codTurno: Int, // Código do turno associado
    val idCheckList: Int, // ID do Checklist associado
    val tipoFert: Int, // Tipo de Fertilizante (se aplicável)
    val horimetro: Double, // Valor do horímetro
    val medicao: Double, // Valor da medição (ex: odômetro)
    val tipo: Int, // Tipo do equipamento (genérico)
    val classif: Int, // Classificação do equipamento
    val flagApontMecan: Boolean, // Flag para apontamento mecânico
    val flagApontPneu: Boolean // Flag para apontamento de pneu
)

// Função de extensão para converter EquipRoomModel para a entidade de domínio Equip
fun EquipRoomModel.roomToEntity(): Equip {
    return with(this){
        Equip(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            codClasse = this.codClasse,
            descrClasse = this.descrClasse,
            codTurno = this.codTurno,
            idCheckList = this.idCheckList,
            tipoFert = this.tipoFert,
            horimetro = this.horimetro,
            medicao = this.medicao,
            tipo = this.tipo,
            classif = this.classif,
            flagApontMecan = this.flagApontMecan,
            flagApontPneu = this.flagApontPneu
        )
    }
}

// Função de extensão para converter a entidade de domínio Equip para EquipRoomModel
fun Equip.entityToRoomModel(): EquipRoomModel {
    return with(this){
        EquipRoomModel(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            codClasse = this.codClasse,
            descrClasse = this.descrClasse,
            codTurno = this.codTurno,
            idCheckList = this.idCheckList,
            tipoFert = this.tipoFert,
            horimetro = this.horimetro,
            medicao = this.medicao,
            tipo = this.tipo,
            classif = this.classif,
            flagApontMecan = this.flagApontMecan,
            flagApontPneu = this.flagApontPneu
        )
    }
}
