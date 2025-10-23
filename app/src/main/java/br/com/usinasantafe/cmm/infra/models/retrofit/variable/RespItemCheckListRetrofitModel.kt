package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.RespItemCheckListRoomModel

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

fun RespItemCheckListRoomModel.roomModelToRetrofitModel(): RespItemCheckListRetrofitModelOutput {
    return with(this){
        RespItemCheckListRetrofitModelOutput(
            id = this.id!!,
            idHeader = this.idHeader,
            idItem = this.idItem,
            option = this.option.ordinal + 1
        )
    }
}

