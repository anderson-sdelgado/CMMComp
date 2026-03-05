package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.utils.required
import java.text.SimpleDateFormat
import java.util.Locale

data class HeaderCheckListRetrofitModelOutput(
    val id: Int,
    val nroEquip: Long,
    val regOperator: Int,
    val nroTurn: Int,
    val dateHour: String,
    val number: Long,
    val respItemList: List<RespItemCheckListRetrofitModelOutput>
)

data class HeaderCheckListRetrofitModelInput(
    val id: Int,
    val idServ: Int,
    val respItemList: List<RespItemCheckListRetrofitModelInput>
)

fun HeaderCheckListRoomModel.roomModelToRetrofitModel(
    number: Long,
    respItemList: List<RespItemCheckListRetrofitModelOutput>
): HeaderCheckListRetrofitModelOutput{
    return with(this) {
        HeaderCheckListRetrofitModelOutput(
            id = ::id.required(),
            nroEquip = nroEquip,
            regOperator = regOperator,
            nroTurn = nroTurn,
            dateHour = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.Builder().setLanguage("pt").setRegion("BR").build()
            ).format(dateHour),
            number = number,
            respItemList = respItemList
        )
    }
}