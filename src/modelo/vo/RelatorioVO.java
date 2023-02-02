package modelo.vo;

public class RelatorioVO {
	private String codigo;
	private int qtd;
	private String valorUnid;
	private String valorTotal;
	private String porcent;
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public String getValorUnid() {
		return valorUnid;
	}
	public void setValorUnid(String valorUnid) {
		this.valorUnid = valorUnid;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getPorcent() {
		return porcent;
	}
	public void setPorcent(String porcentVenda) {
		this.porcent = porcentVenda;
	}
}
