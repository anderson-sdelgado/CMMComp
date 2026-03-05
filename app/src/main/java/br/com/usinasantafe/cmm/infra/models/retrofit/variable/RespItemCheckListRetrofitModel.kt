package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.utils.required

data class RespItemCheckListRetrofitModelOutput(
    val id: Int,
    val idHeader: Int,
    val idItem: Int,
    val option: Int,
)

data class RespItemCheckListRetrofitModelInput(
    val id: Int,
    val idServ: Int
)

fun ItemRespCheckListRoomModel.roomModelToRetrofitModel(): RespItemCheckListRetrofitModelOutput {
    return with(this){
        RespItemCheckListRetrofitModelOutput(
            id = ::id.required(),
            idHeader = idHeader,
            idItem = idItem,
            option = option.ordinal + 1
        )
    }
}

