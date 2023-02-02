package controle;

import java.util.ArrayList;

import modelo.dao.PedidoDAO;
import modelo.dao.ProducaoDAO;
import modelo.dao.EmpresaDAO;
import modelo.dao.LocalizacoesDAO;
import modelo.dao.OrcamentoDAO;
import modelo.dao.ProdutoDAO;
import modelo.dao.RelatorioDAO;
import modelo.vo.PedidoVO;
import modelo.vo.ProducaoVO;
import modelo.vo.EmpresaVO;
import modelo.vo.ItensPedidoVO;
import modelo.vo.ItensProducaoVO;
import modelo.vo.LocalizacaoVO;
import modelo.vo.OrcamentoVO;
import modelo.vo.ProdutoVO;
import modelo.vo.RelatorioVO;

public class LumanferControle {
	private ProdutoDAO produtoDAO;
	private LocalizacoesDAO lozalizacoesDAO;
	private EmpresaDAO empresaDAO;
	private PedidoDAO pedidoDAO;
	private RelatorioDAO relatorioDAO;
	private ProducaoDAO producaoDAO;
	private OrcamentoDAO orcamentoDAO;
	
	public LumanferControle() {
		produtoDAO = new ProdutoDAO();
		lozalizacoesDAO = new LocalizacoesDAO(); 
		empresaDAO = new EmpresaDAO();
		pedidoDAO = new PedidoDAO();
		relatorioDAO = new RelatorioDAO();
		producaoDAO = new ProducaoDAO();
		orcamentoDAO = new OrcamentoDAO();
	}

	public ArrayList<ProdutoVO> getCodigos() {
		return produtoDAO.getCodigos();
	}

	public int getQtdProdutos() {
		return produtoDAO.getQtdProdutos();
	}

	public ProdutoVO getProduto(String codigo) {
		return produtoDAO.getProduto(codigo);
	}

	public int getQtdLocalizacoes() {
		return lozalizacoesDAO.getQtdLocalizacoes();
	}

	public ArrayList<LocalizacaoVO> getLocalizacoes(String codigo, String desc) {
		return lozalizacoesDAO.getLocalizacoes(codigo, desc);
	}

	public boolean alteraProduto(ProdutoVO produtoVO) {
		return produtoDAO.alteraProduto(produtoVO);
	}

	public boolean existeProduto(String codigo) {
		return produtoDAO.existeProduto(codigo);
	}

	public boolean adicionaProduto(ProdutoVO produtoVO) {
		return produtoDAO.adicionaProduto(produtoVO);
	}

	public int getIdProduto(String codigo) {
		return produtoDAO.getIdProduto(codigo);
	}

	public ArrayList<ProdutoVO> getProdutos(String codigo, String descricao, String codAlt, String local) {
		return produtoDAO.getProdutos(codigo, descricao, codAlt, local);
	} 
	
	public int getQtdEmpresas() {
		return empresaDAO.getQtdEmpresas();
	}

	public ArrayList<EmpresaVO> getEmpresas(String codigo, String desc) {
		return empresaDAO.getEmpresas(codigo, desc);
	}

	public boolean adicionaPedido(PedidoVO pedidoVO, ArrayList<ItensPedidoVO> itensTabela) {
		return pedidoDAO.adicionaPedido(pedidoVO, itensTabela);
	}

	public int getQtdPedidos() {
		return pedidoDAO.getQtdPedidos();
	}

	public ArrayList<PedidoVO> getPedidos(String codigo, String empresa, String status, String limite) {
		return pedidoDAO.getPedidos(codigo, empresa, status, limite);
	}

	public ArrayList<String> getIdPedidos() {
		return pedidoDAO.getIdPedidos();
	}

	public ArrayList<ItensPedidoVO> getProdutosPedido(String id) {
		return pedidoDAO.getProdutosPedido(id);
	}

	public PedidoVO getPedido(String id) {
		return pedidoDAO.getPedido(id);
	}

	public int getQtdEstoque(String codigo) {
		return produtoDAO.getQtdEstoque(codigo);
	}

	public boolean excluiProduto(String codigo) {
		return produtoDAO.excluiProduto(codigo);
	}

	public int getQtdProdutosPedido(String id) {
		return pedidoDAO.getQtdProdutosPedido(id);
	}

	public boolean existePedido(String codigo) {
		return pedidoDAO.existeIdPedido(codigo);
	}
	
	public boolean adicionaEmpresa(EmpresaVO e) {
		return empresaDAO.adicionaEmpresa(e);
	}

	public boolean alteraPedido(PedidoVO p, ArrayList<ItensPedidoVO> itensTabela) {
		return pedidoDAO.alteraPedido(p, itensTabela);
	}

	public boolean excluiPedido(String codigo, ArrayList<ItensPedidoVO> itensTabela) {
		return pedidoDAO.excluiPedido(codigo, itensTabela);
	}

	public int getIdNovoProduto() {
		return produtoDAO.getIdNovoProduto();
	}

	public boolean existeLocal(String descricao) {
		return lozalizacoesDAO.existeLocal(descricao);
	}

	public boolean addLocal(String descricao) {
		return lozalizacoesDAO.addLocal(descricao);
	}

	public boolean removeProdutoPedidos(int idProduto) {
		return pedidoDAO.removeProdutoPedidos(idProduto);
	}

	public ArrayList<RelatorioVO> getRelatorioVendas(String dataInicio, String dataFim) {
		return relatorioDAO.getVendas(dataInicio, dataFim);
	}

	public boolean removeEmpresa(String codigo) {
		return empresaDAO.removeEmpresa(codigo);
	}

	public boolean removeLocal(int id) {
		return lozalizacoesDAO.removeLocal(id);
	}

	public String getIdLocal(String desc) {
		return lozalizacoesDAO.getIdLocal(desc);
	}

	public EmpresaVO getEmpresa(String id) {
		return empresaDAO.getEmpresa(id);
	}

	public boolean atualizaEmpresa(EmpresaVO e) {
		return empresaDAO.atualizaEmpresa(e);
	}

	public boolean atualizaLocal(LocalizacaoVO l) {
		return lozalizacoesDAO.atualizaLocal(l);
	}

	public ArrayList<ProducaoVO> getProducoes(String id, String data) {
		return producaoDAO.getProducoes(id, data);
	}

	public int adicionaProducao(ProducaoVO p, ArrayList<ItensProducaoVO> itensTabela) {
		return producaoDAO.adicionaProducao(p, itensTabela);
	}

	public ProducaoVO getProducao(int id) {
		return producaoDAO.getProducao(id);
	}

	public ArrayList<ItensProducaoVO> getProdutosProducao(int id) {
		return producaoDAO.getProdutosProducao(id);
	}

	public boolean atualizaProducao(ProducaoVO p, ArrayList<ItensProducaoVO> itensTabela) {
		return producaoDAO.atualizaProducao(p, itensTabela);
	}

	public boolean removeProducao(int id) {
		return producaoDAO.removeProducao(id);
	}

	public ArrayList<EmpresaVO> getRelatorioVendasEmpresas(String dataInicio, String dataFim) {
		return relatorioDAO.getRelatorioVendasEmpresas(dataInicio, dataFim);
	}

	public ArrayList<RelatorioVO> getRelatorioABC(String dataInicio, String dataFim, String codigo) {
		return relatorioDAO.getRelatorioABC(dataInicio, dataFim, codigo);
	}

	public ArrayList<EmpresaVO> getRelatorioABCProdutos(String codigo) {
		return relatorioDAO.getRelatorioABCProdutos(codigo);
	}

	public int getNovoNumOrcamento() {
		return orcamentoDAO.getIdNovoOrcamento();
	}

	public boolean alteraOrcamentoPedido(String pedido, int numOrcamento) {
		return pedidoDAO.alteraOrcamentoPedido(pedido, numOrcamento);
	}

	public ArrayList<OrcamentoVO> getOrcamentos(String id, String emissao, String empresaSelecionada) {
		return orcamentoDAO.getOrcamentos(id, emissao, empresaSelecionada);
	}

	public String getValorProduto(int idProduto) {
		return produtoDAO.getValorProduto(idProduto);
	}

	public boolean adicionaOrcamento(OrcamentoVO o) {
		return orcamentoDAO.adicionaOrcamento(o);
	}

	public boolean atualizaOrcamento(OrcamentoVO o) {
		return orcamentoDAO.atualizaOrcamento(o);
	}

	public OrcamentoVO getOrcamento(int id) {
		return orcamentoDAO.getOrcamento(id);
	}

	public String getCodigoAlternativo(String string) {
		return produtoDAO.getCodigoAlternativo(string);
	}

	public boolean excluiOrcamento(int codigo) {
		return orcamentoDAO.excluiOrcamento(codigo);
	}

	public String getLocalProduto(String codigoProduto) {
		return produtoDAO.getLocalProduto(codigoProduto);
	}

	public String getRevisaoProduto(String codigoProduto) {
		return produtoDAO.getRevisaoProduto(codigoProduto);
	}
}
