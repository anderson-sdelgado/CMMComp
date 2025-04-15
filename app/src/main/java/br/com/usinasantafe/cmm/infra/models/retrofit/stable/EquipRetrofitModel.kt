package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip

data class EquipRetrofitModel(
    val idEquip: Int,
    val nroEquip: Long,
    val codClasse: Int,
    val descrClasse: String,
    val codTurno: Int,
    val idCheckList: Int,
    val tipoFert: Int,
    val horimetro: Double,
    val medicao: Double,
    val tipo: Int,
    val classif: Int,
    val flagApontMecan: Boolean,
    val flagApontPneu: Boolean,
)

fun EquipRetrofitModel.retrofitModelToEntity(): Equip {
    return Equip(
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
