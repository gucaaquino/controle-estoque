package modelo.vo;

import java.util.ArrayList;

public class OrcamentoVO {
	private int id;
	private int idEmpresa;
	private String pedido;
	private String cliente;
	private String empresa;
	private String valorProposta;
	private String condPagamento;
	private String emissao;
	private String obs;
	private ArrayList<ItensPedidoVO> itens;
	
	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public int getId() {
		return id;
	}
	public void setId(int numOrcamento) {
		this.id = numOrcamento;
	}
	public String getPedido() {
		return pedido;
	}
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String contato) {
		this.empresa = contato;
	}
	public String getValProposta() {
		return valorProposta;
	}
	public void setValProposta(String valorProposta) {
		this.valorProposta = valorProposta;
	}
	public String getCondPagamento() {
		return condPagamento;
	}
	public void setCondPagamento(String condPagamento) {
		this.condPagamento = condPagamento;
	}
	public String getEmissao() {
		return emissao;
	}
	public void setEmissao(String prazoEntrega) {
		this.emissao = prazoEntrega;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public ArrayList<ItensPedidoVO> getItens() {
		return itens;
	}
	public void setItens(ArrayList<ItensPedidoVO> itens) {
		this.itens = itens;
	}
}
