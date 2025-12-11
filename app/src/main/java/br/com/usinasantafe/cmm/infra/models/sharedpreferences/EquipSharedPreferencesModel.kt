package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.lib.TypeEquip

data class EquipSharedPreferencesModel(
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeEquip: TypeEquip,
    var hourMeter: Double,
    val classify: Int,
    val flagMechanic: Boolean,
    val flagTire: Boolean,
)

fun EquipSharedPreferencesModel.sharedPreferencesModelToEntity(): Equip {
    return with(this) {
        Equip(
            id = id,
            nro = nro,
            codClass = codClass,
            descrClass = descrClass,
            codTurnEquip = codTurnEquip,
            idCheckList = idCheckList,
            typeEquip = typeEquip,
            hourMeter = hourMeter,
            classify = classify,
            flagMechanic = flagMechanic,
            flagTire = flagTire
        )
    }
}

fun Equip.entityToSharedPreferencesModel(): EquipSharedPreferencesModel {
    return with(this) {
        EquipSharedPreferencesModel(
            id = id,
            nro = nro,
            codClass = codClass,
            descrClass = descrClass,
            codTurnEquip = codTurnEquip,
            idCheckList = idCheckList,
            typeEquip = typeEquip,
            hourMeter = hourMeter,
            classify = classify,
            flagMechanic = flagMechanic,
            flagTire = flagTire
            )
    }
}