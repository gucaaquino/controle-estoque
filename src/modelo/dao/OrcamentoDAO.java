package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.vo.ItensPedidoVO;
import modelo.vo.OrcamentoVO;

public class OrcamentoDAO {
	public int getIdNovoOrcamento() {
		int num = 1;

		String comandoSQL = "SELECT MAX(id) as num FROM Orcamento;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				num = resultado.getInt("num") + 1;
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return num;
	}

	public ArrayList<OrcamentoVO> getOrcamentos(String id, String emissao, String empresaSelecionada) {
		ArrayList<OrcamentoVO> orcamentos = new ArrayList<OrcamentoVO>();

		OrcamentoVO orcamentoVO = null;
		String comandoSQL = "SELECT Orcamento.id, dataEmissao, Empresa.descricao, valor FROM Orcamento, Empresa WHERE Orcamento.id LIKE '"
				+ id
				+ "%' AND dataEmissao LIKE '%"
				+ emissao
				+ "%' AND Empresa.descricao LIKE '"
				+ empresaSelecionada
				+ "%' AND Empresa.id = idEmpresa ORDER BY id DESC LIMIT 50;";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				orcamentoVO = new OrcamentoVO();

				orcamentoVO.setId(resultado.getInt("Orcamento.id"));
				orcamentoVO.setEmissao(transformaDateString(resultado.getString("dataEmissao")));
				orcamentoVO.setEmpresa(resultado.getString("Empresa.descricao"));
				orcamentoVO.setValProposta(resultado.getString("valor"));

				orcamentos.add(orcamentoVO);
			}

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				resultado.close();
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return orcamentos;
	}

	public String transformaDateString(String dataBanco) {
		Date data = new Date();
		SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");

		try {
			data = formatoBanco.parse(dataBanco);
		} catch (ParseException e) {

		}

		SimpleDateFormat formatoUsual = new SimpleDateFormat("yyyy-MM-dd");

		String dataString = formatoUsual.format(data); 

		return dataString;
	}

	public boolean adicionaOrcamento(OrcamentoVO o) {
		boolean insercao = true;

		String comandoSQL = "INSERT INTO Orcamento (id, dataEmissao, idEmpresa, cliente, condPagamento, obs, valor) VALUES ( "
				+ o.getId()
				+ ", '"
				+ o.getEmissao()
				+ "', "
				+ o.getIdEmpresa()
				+ ", '"
				+ o.getCliente()
				+ "', '"
				+ o.getCondPagamento()
				+ "', '"
				+ o.getObs()
				+ "', '"
				+ o.getValProposta()
				+ "');";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado == 0)
				insercao = false;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		if(!adicionaItensOrcamento(o.getItens(), o.getId()))
			insercao = false;

		return insercao;
	}

	public boolean adicionaItensOrcamento(ArrayList<ItensPedidoVO> itensTabela, int idOrc) {
		String comandoSQL;
		int qtdProdutos = 0;

		for(ItensPedidoVO i : itensTabela) {
			String codAlt = i.getCodAlternativo();

			if(codAlt.equals(""))
				codAlt = "";

			comandoSQL = "INSERT INTO ItensOrcamento (idProduto, idOrcamento, qtd, descricao, codAlternativo, valor) VALUES ( "
					+ i.getIdProduto()
					+ ", '"
					+ idOrc
					+ "', "
					+ i.getQtd()
					+ ", '"
					+ i.getDescricaoProduto()
					+ "', '"
					+ codAlt
					+ "', '"
					+ i.getValorUnid().replace(",", ".")
					+ "');";

			try {
				Statement comando = ConexaoBD.getConexaoBD().createStatement();
				int resultado = comando.executeUpdate(comandoSQL);

				if(resultado != 0)
					qtdProdutos += 1;

				comando.close();

			}catch (SQLException e) {
				System.err.println("Erro ao realizar conexao com o banco "
						+ "verifique a url de conexão");
				e.printStackTrace();
			}

		}

		if(qtdProdutos != itensTabela.size())
			return false;

		return true;
	}

	public boolean atualizaOrcamento(OrcamentoVO o) {
		boolean atualizar = true;

		String comandoSQL = "UPDATE Orcamento SET idEmpresa = '"
				+ o.getIdEmpresa()
				+ "', cliente = '"
				+ o.getCliente()
				+ "', condPagamento = '"
				+ o.getCondPagamento()
				+ "', obs = '"
				+ o.getObs()
				+ "', valor = '"
				+ o.getValProposta()
				+ "' WHERE id = "
				+ o.getId()
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado == 0)
				atualizar = false;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		removeItensOrcamento(o.getId());

		if(!adicionaItensOrcamento(o.getItens(), o.getId()))
			atualizar = false;

		return atualizar;
	}

	private boolean removeItensOrcamento(int id) {
		String comandoSQL;
		int resultado = 0;

		comandoSQL = "DELETE FROM ItensOrcamento WHERE idOrcamento = "
				+ id
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeUpdate(comandoSQL);
			
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}



		if(resultado != 0)
			return false;

		return true;
	}

	public OrcamentoVO getOrcamento(int id) {
		OrcamentoVO orcamentoVO = null;
		
		String comandoSQL = "SELECT Orcamento.id, dataEmissao, Empresa.descricao, valor, condPagamento, obs, cliente FROM Orcamento, Empresa WHERE Orcamento.id = "
				+ id
				+ " AND Empresa.id = idEmpresa;";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				orcamentoVO = new OrcamentoVO();

				orcamentoVO.setId(resultado.getInt("Orcamento.id"));
				orcamentoVO.setEmissao(transformaDateString(resultado.getString("dataEmissao")));
				orcamentoVO.setEmpresa(resultado.getString("Empresa.descricao"));
				orcamentoVO.setValProposta(resultado.getString("valor"));
				orcamentoVO.setCliente(resultado.getString("cliente"));
				orcamentoVO.setCondPagamento(resultado.getString("condPagamento"));
				orcamentoVO.setObs(resultado.getString("obs"));
				orcamentoVO.setItens(getProdutosOrcamento(id));
			}

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				resultado.close();
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return orcamentoVO;
	}
	
	public ArrayList<ItensPedidoVO> getProdutosOrcamento(int id){
		ProdutoDAO p = new ProdutoDAO();
		ArrayList<ItensPedidoVO> produtos = new ArrayList<ItensPedidoVO>();
		
		ItensPedidoVO i = null;
		
		String comandoSQL = "SELECT * FROM ItensOrcamento WHERE idOrcamento ="
				+ id
				+ ";";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				i = new ItensPedidoVO();
				
				i.setIdProduto(resultado.getInt("idProduto"));
				i.setQtd(resultado.getInt("qtd"));
				i.setValorUnid(resultado.getFloat("valor") + "");
				
				String codAlternativo = resultado.getString("codAlternativo");
				
				if(codAlternativo == null)
					codAlternativo = "";
				
				i.setCodAlternativo(codAlternativo);
				i.setDescricaoProduto(resultado.getString("descricao"));
				i.setCodigoProduto(p.getCodigoProduto(resultado.getInt("idProduto")));
				i.setStatus(true);
				
				produtos.add(i);
			}

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				resultado.close();
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return produtos;
	}
	
	public boolean removeProdutoOrcamentos(int id) {
		String comandoSQL = "DELETE FROM ItensOrcamento WHERE idOrcamento = "
				+ id
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			comando.close();

			if(resultado == 0) 
				return false;

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return true;
	}

	public boolean excluiOrcamento(int codigo) {
		PedidoDAO p = new PedidoDAO();
		
		p.excluiPedidosOrcamento(codigo);
		removeItensOrcamento(codigo);
		
		boolean deletar = true;

		String comandoSQL = "DELETE FROM Orcamento WHERE id = "
				+ codigo
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);
			
			if(resultado == 0)
				deletar = false;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		removeItensOrcamento(codigo);

		return deletar;
	}
}
