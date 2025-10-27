package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Produto // Import da entidade de domínio Produto
import br.com.usinasantafe.cmm.utils.TB_PRODUTO // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PRODUTO)
data class ProdutoRoomModel (
    @PrimaryKey
    val idProduto: Int,
    val codProduto: Int,
    val descrProduto: String
)

fun ProdutoRoomModel.roomModelToEntity(): Produto {
    return with(this){
        Produto(
            idProduto = this.idProduto,
            codProduto = this.codProduto,
            descrProduto = this.descrProduto
        )
    }
}

fun Produto.entityProdutoToRoomModel(): ProdutoRoomModel {
    return with(this){
        ProdutoRoomModel(
            idProduto = this.idProduto,
            codProduto = this.codProduto,
            descrProduto = this.descrProduto
        )
    }
}
