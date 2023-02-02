package modelo.vo;

public class PedidoVO {
	private String id;
	private int numeroOrc;
	private String dataEmissao;
	private String dataEntrega;
	private String empresa;
	private String produtosProntos;
	private String porcentConclusao;
	private String status;
	private int idEmpresa;
	private int qtdProdutos;
	
	public String getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getProdutosProntos() {
		return produtosProntos;
	}
	public void setProdutosProntos(String produtosProntos) {
		this.produtosProntos = produtosProntos;
	}
	public String getPorcentConclusao() {
		return porcentConclusao;
	}
	public void setPorcentConclusao(String porcentConclusao) {
		this.porcentConclusao = porcentConclusao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getQtdProdutos() {
		return qtdProdutos;
	}
	public void setQtdProdutos(int qtdProdutos) {
		this.qtdProdutos = qtdProdutos;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrcamento() {
		return numeroOrc;
	}
	public void setNumeroOrc(int numeroOrc) {
		this.numeroOrc = numeroOrc;
	}
	public String getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}
