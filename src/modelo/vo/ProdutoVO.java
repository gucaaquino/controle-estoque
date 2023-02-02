package modelo.vo;

public class ProdutoVO {
	private int id;
	private String codigo;
	private String revisao;
	private String descricao;
	private String valor;
	private int qtd;
	private String ultima_modificacao;
	private String localizacao;
	private String observacao;
	private String codAlternativo;
	
	public String getCodAlternativo() {
		return codAlternativo;
	}
	public void setCodAlternativo(String codAlternativo) {
		this.codAlternativo = codAlternativo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRevisao() {
		return revisao;
	}
	public void setRevisao(String revisao) {
		this.revisao = revisao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public String getUltima_modificacao() {
		return ultima_modificacao;
	}
	public void setUltima_modificacao(String ultima_modificacao) {
		this.ultima_modificacao = ultima_modificacao;
	}
	

}
