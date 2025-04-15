package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Produto

data class ProdutoRetrofitModel(
    val idProduto: Int,
    val codProduto: Int,
    val descrProduto: String,
)

fun ProdutoRetrofitModel.retrofitModelToEntity(): Produto {
    return Produto(
        idProduto = this.idProduto,
        codProduto = this.codProduto,
        descrProduto = this.descrProduto
    )
}
