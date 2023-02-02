package modelo.vo;

public class ItensProducaoVO {
	private int idProduto;
	private int idProducao;
	private int qtd;
	private String codigoProduto;
	private String descricaoProduto;
	
	public String getDescricaoProduto() {
		return descricaoProduto;
	}
	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}
	public String getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public int getIdProducao() {
		return idProducao;
	}
	public void setIdProducao(int idProducao) {
		this.idProducao = idProducao;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
