package modelo.vo;

public class ProducaoVO {
	
	private int id;
	private String dataProducao;
	private int qtdProdutos;
	
	public int getQtdProdutos() {
		return qtdProdutos;
	}
	public void setQtdProdutos(int qtdProdutos) {
		this.qtdProdutos = qtdProdutos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataProducao() {
		return dataProducao;
	}
	public void setDataProducao(String dataProducao) {
		this.dataProducao = dataProducao;
	}
}
