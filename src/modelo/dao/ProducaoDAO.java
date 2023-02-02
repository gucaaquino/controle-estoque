package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.vo.ItensProducaoVO;
import modelo.vo.ProducaoVO;

public class ProducaoDAO {

	public ArrayList<ProducaoVO> getProducoes(String id, String data) {
		ArrayList<ProducaoVO> producoes = new ArrayList<ProducaoVO>();

		ProducaoVO producaoVO = null;
		String comandoSQL = "SELECT id, dataProducao FROM Producao WHERE id LIKE '"
				+ id
				+ "%' AND dataProducao LIKE '"
				+ data
				+ "%' GROUP BY id, dataProducao ORDER BY id LIMIT 50;";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				producaoVO = new ProducaoVO();
				producaoVO.setId(resultado.getInt("Producao.id"));
				producaoVO.setDataProducao(transformaDateString(resultado.getString("dataProducao")));
				producaoVO.setQtdProdutos(getQtdProdutosProducao(producaoVO.getId()));

				producoes.add(producaoVO);
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

		return producoes;
	}

	public int getQtdProdutosProducao(int id) {
		int qtd = 0;

		String comandoSQL = "SELECT SUM(qtd) FROM ItensProducao WHERE idProducao = "
				+ id
				+ ";";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				qtd = resultado.getInt(1);
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

		return qtd;
	}

	public int adicionaProducao(ProducaoVO p, ArrayList<ItensProducaoVO> itensTabela) {
		int insercao = 0;
		int id = getIdNovoProducao();

		String comandoSQL = "INSERT INTO Producao (id, dataProducao) VALUES( "
				+ id
				+ ", '"
				+ p.getDataProducao()
				+ "');";

		Statement comando = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			insercao = comando.executeUpdate(comandoSQL);

			if(insercao != 0)
				adicionaItensProducao(id, itensTabela);

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		if(insercao != 0)
			return id;
		else
			return 0;
	}

	public boolean adicionaItensProducao(int idProducao, ArrayList<ItensProducaoVO> itensTabela) {
//		ProdutoDAO p = new ProdutoDAO();

		String comandoSQL;
		int qtdProdutos = 0;

		Statement comando = null;

		for(ItensProducaoVO i : itensTabela) {
			comandoSQL = "INSERT INTO ItensProducao (idProducao, idProduto, qtd) VALUES ( "
					+ idProducao
					+ ", "
					+ i.getIdProduto()
					+ ", "
					+ i.getQtd()
					+ ")";

//			p.alteraQuantidade(i.getCodigoProduto(), i.getQtd());

			try {
				comando = ConexaoBD.getConexaoBD().createStatement();
				qtdProdutos += comando.executeUpdate(comandoSQL);

			}catch (SQLException e) {
				System.err.println("Erro ao realizar conexao com o banco "
						+ "verifique a url de conexão");
				e.printStackTrace();
			}finally {
				try {
					comando.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if(qtdProdutos != itensTabela.size())
			return false;
		else
			return true;
	}

	public int getIdNovoProducao() {
		int id = 0;

		String comandoSQL = "SELECT MAX(id) FROM Producao;";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				id = resultado.getInt(1) + 1;
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

		return id;
	}

	public ProducaoVO getProducao(int id) {
		ProducaoVO producaoVO = null;
		String comandoSQL = "SELECT id, dataProducao FROM Producao WHERE id = "
				+ id
				+ ";";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				producaoVO = new ProducaoVO();
				producaoVO.setId(resultado.getInt("Producao.id"));
				producaoVO.setDataProducao(transformaDateString(resultado.getString("dataProducao")));
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

		return producaoVO;
	}

	public ArrayList<ItensProducaoVO> getProdutosProducao(int id) {
		ProdutoDAO p = new ProdutoDAO();

		ArrayList<ItensProducaoVO> produtos = new ArrayList<ItensProducaoVO>();

		ItensProducaoVO produto = null;
		String comandoSQL = "SELECT idProduto, idProducao, qtd FROM ItensProducao WHERE idProducao = "
				+ id
				+ ";";

		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				produto = new ItensProducaoVO();
				produto.setIdProduto(resultado.getInt("idProduto"));
				produto.setCodigoProduto(p.getCodigoProduto(resultado.getInt("idProduto")));
				produto.setDescricaoProduto(p.getDescProduto(resultado.getInt("idProduto")));
				produto.setIdProducao(id);
				produto.setQtd(resultado.getInt("qtd"));

				produtos.add(produto);
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

	public boolean atualizaProducao(ProducaoVO producao, ArrayList<ItensProducaoVO> itensTabela) {
		int insercao = 0;
		int id = producao.getId();

		String comandoSQL = "UPDATE Producao SET dataProducao = '"
				+ producao.getDataProducao()
				+ "' WHERE id = "
				+ producao.getId()
				+ ";";

		Statement comando = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			insercao = comando.executeUpdate(comandoSQL);

			if(insercao != 0) {
				removeItensProducao(producao.getId(), getProdutosProducao(producao.getId()));
				adicionaItensProducao(id, itensTabela);
			}

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		if(insercao != 0)
			return true;
		else
			return false;
	}

	private boolean removeItensProducao(int id, ArrayList<ItensProducaoVO> produtosProducao) {
//		ProdutoDAO p = new ProdutoDAO();
//		for(ItensProducaoVO i : produtosProducao) {
//			p.alteraQuantidade(i.getCodigoProduto(), -i.getQtd());
//		}

		Statement comando = null;
		int resultado = 0;

		String comandoSQL = "DELETE FROM ItensProducao WHERE idProducao = "
				+ id
				+ ";";

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeUpdate(comandoSQL);

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(resultado != 0)
			return true;
		else
			return false;
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

	public boolean removeProducao(int id) {
		removeItensProducao(id, getProdutosProducao(id));
		boolean remocao = false;

		String comandoSQL = "DELETE FROM Producao WHERE id = "
				+ id
				+ ";";

		Statement comando = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0)
				remocao = true;

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return remocao;
	}

	public boolean removeProdutoProducoes(int idProduto) {
		boolean remocao = false;

		String comandoSQL = "DELETE FROM ItensProducao WHERE idProduto = "
				+ idProduto
				+ ";";

		Statement comando = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0)
				remocao = true;

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return remocao;
	}

}
