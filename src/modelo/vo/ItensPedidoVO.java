package modelo.vo;

public class ItensPedidoVO {
	private int idProduto;
	private String idPedido;
	private int qtd;
	private int qtdEstoque;
	private String codigoProduto;
	private String descricaoProduto;
	private boolean status;
	private String valorUnid;
	private float valorTot;
	private String codAlternativo;
	
	public String getCodAlternativo() {
		return codAlternativo;
	}
	public void setCodAlternativo(String codAlternativo) {
		this.codAlternativo = codAlternativo;
	}
	public String getValorUnid() {
		return valorUnid;
	}
	public void setValorUnid(String valorUnid) {
		this.valorUnid = valorUnid;
	}
	public float getValorTot() {
		return valorTot;
	}
	public void setValorTot(float valorTot) {
		this.valorTot = valorTot;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
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
	public String getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
