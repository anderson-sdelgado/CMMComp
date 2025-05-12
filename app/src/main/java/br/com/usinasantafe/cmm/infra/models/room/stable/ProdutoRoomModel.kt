package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Produto // Import da entidade de domínio Produto
import br.com.usinasantafe.cmm.utils.TB_PRODUTO // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PRODUTO) // Define como entidade do Room e especifica o nome da tabela
data class ProdutoRoomModel (
    @PrimaryKey // Define idProduto como chave primária
    val idProduto: Int,
    val codProduto: Int,
    val descrProduto: String
)

// Função de extensão para converter ProdutoRoomModel para a entidade de domínio Produto
fun ProdutoRoomModel.roomModelToEntity(): Produto {
    return with(this){
        Produto(
            idProduto = this.idProduto,
            codProduto = this.codProduto,
            descrProduto = this.descrProduto
        )
    }
}

// Função de extensão para converter a entidade de domínio Produto para ProdutoRoomModel
fun Produto.entityToRoomModel(): ProdutoRoomModel {
    return with(this){
        ProdutoRoomModel(
            idProduto = this.idProduto,
            codProduto = this.codProduto,
            descrProduto = this.descrProduto
        )
    }
}
