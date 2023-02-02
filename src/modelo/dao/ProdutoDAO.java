package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import modelo.vo.ProdutoVO;

public class ProdutoDAO {
	public ArrayList<ProdutoVO> getCodigos() {
		ProdutoVO produtoVO;
		ArrayList<ProdutoVO> codigos = new ArrayList<ProdutoVO>();

		String comandoSQL = "SELECT id, codigo, descricao FROM Produto ORDER BY codigo;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				produtoVO = new ProdutoVO();
				produtoVO.setId(resultado.getInt("id"));
				produtoVO.setCodigo(resultado.getString("codigo"));
				produtoVO.setDescricao(resultado.getString("descricao"));

				codigos.add(produtoVO);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return codigos;
	}

	public int getQtdProdutos() {
		int qtd = 0;

		String comandoSQL = "SELECT count(*) FROM Produto;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				qtd = resultado.getInt(1);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return qtd;
	}

	public ProdutoVO getProduto(String codigo) {
		ProdutoVO produtoVO = null;
		String comandoSQL = "SELECT Produto.id, Produto.codigo, Produto.descricao, quantidade, valor, revisao, Localizacao.descricao, codAlternativo, ultima_modificacao, observacao FROM Produto, Localizacao WHERE Produto.codigo = '"
				+ codigo
				+ "' AND Localizacao.id = idLocal;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				produtoVO = new ProdutoVO();

				produtoVO.setId(resultado.getInt("Produto.id"));
				produtoVO.setCodigo(resultado.getString("Produto.codigo"));
				produtoVO.setDescricao(resultado.getString("Produto.descricao"));
				produtoVO.setLocalizacao(resultado.getString("Localizacao.descricao"));
				produtoVO.setQtd(resultado.getInt("quantidade"));
				produtoVO.setRevisao(resultado.getString("revisao"));
				produtoVO.setUltima_modificacao(resultado.getString("ultima_modificacao"));
				produtoVO.setValor(resultado.getFloat("valor") + "");
				produtoVO.setObservacao(resultado.getString("observacao"));
				produtoVO.setCodAlternativo(resultado.getString("codAlternativo"));
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return produtoVO;
	}
	
	public String getValorProduto(int idProduto) {
		String valor = "";
		
		String comandoSQL = "SELECT valor FROM Produto WHERE id = "
				+ idProduto
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next())
				valor = resultado.getString(1);

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}
		
		return valor;
	}

	public boolean alteraProduto(ProdutoVO produtoVO) {
		String comandoSQL = "UPDATE Produto SET descricao = '"
				+ produtoVO.getDescricao()
				+ "', quantidade = "
				+ produtoVO.getQtd()
				+ ", valor = '"
				+ produtoVO.getValor()
				+ "', revisao = '"
				+ produtoVO.getRevisao()
				+ "', ultima_modificacao = '"
				+ produtoVO.getUltima_modificacao()
				+ "', idLocal = "
				+ produtoVO.getLocalizacao()
				+ ", observacao = '"
				+ produtoVO.getObservacao()
				+ "', codAlternativo = '"
				+ produtoVO.getCodAlternativo()
				+ "' WHERE id = "
				+ produtoVO.getId()
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0) {
				return true;
			}

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return false;
	}

	public boolean existeProduto(String codigo) {
		String comandoSQL = "SELECT COUNT(*) FROM Produto WHERE codigo = '"
				+ codigo
				+ "';";
		
		int qtd = 0;

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next()) {
				qtd = resultado.getInt(1);
			}
			
			if(qtd != 0)
				return true;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return false;
	}

	public boolean adicionaProduto(ProdutoVO produtoVO) {
		String comandoSQL = "INSERT INTO Produto (id, descricao, quantidade, valor, revisao, ultima_modificacao, idLocal, codigo, observacao, codAlternativo) VALUES ( "
				+ getIdNovoProduto()
				+ ",'"
				+ produtoVO.getDescricao()
				+ "', "
				+ produtoVO.getQtd()
				+ ", '"
				+ produtoVO.getValor()
				+ "', '"
				+ produtoVO.getRevisao()
				+ "', '"
				+ produtoVO.getUltima_modificacao()
				+ "', "
				+ produtoVO.getLocalizacao()
				+ ", '"
				+ produtoVO.getCodigo()
				+ "', '"
				+ produtoVO.getObservacao()
				+ "', '"
				+ produtoVO.getCodAlternativo()
				+ "');";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0) {
				return true;
			}

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return false;
	}

	public int getIdProduto(String codigo) {
		int id = 0;

		String comandoSQL = "SELECT id FROM Produto WHERE codigo = '"
				+ codigo
				+ "';";
		
		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				id = resultado.getInt(1);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return id;
	}
	
	public String getCodigoProduto(int id) {
		String codigo = null;

		String comandoSQL = "SELECT codigo FROM Produto WHERE id = "
				+ id
				+ ";";
		
		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				codigo = resultado.getString(1);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return codigo;
	}
	
	public String getCodigoAlternativo(String cod) {
		String codigo = "";

		String comandoSQL = "SELECT codAlternativo FROM Produto WHERE codigo = '"
				+ cod
				+ "';";
		
		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				if(resultado.getString(1) != null)
					codigo = resultado.getString(1);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return codigo;
	}

	public ArrayList<ProdutoVO> getProdutos(String codigo, String descricao, String codALt, String local) {
		ArrayList<ProdutoVO> produtos = new ArrayList<ProdutoVO>();
		
		ProdutoVO produtoVO = null;
		String comandoSQL = "SELECT Produto.id, Produto.codigo, Produto.descricao, codAlternativo, quantidade, valor, revisao, Localizacao.descricao, ultima_modificacao FROM Produto, Localizacao WHERE Produto.codigo LIKE '"
				+ codigo
				+ "%' AND Produto.descricao LIKE '%"
				+ descricao
				+ "%' AND Localizacao.descricao LIKE '"
				+ local
				+ "%' AND codAlternativo LIKE '"
				+ codALt
				+ "%' AND Localizacao.id = idLocal ORDER BY id LIMIT 50;";
		
		Statement comando = null;
		ResultSet resultado = null;

		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				produtoVO = new ProdutoVO();

				produtoVO.setId(resultado.getInt("Produto.id"));
				produtoVO.setCodigo(resultado.getString("Produto.codigo"));
				produtoVO.setDescricao(resultado.getString("Produto.descricao"));
				produtoVO.setLocalizacao(resultado.getString("Localizacao.descricao"));
				produtoVO.setQtd(resultado.getInt("quantidade"));
				produtoVO.setRevisao(resultado.getString("revisao"));
				produtoVO.setUltima_modificacao(transformaDateString(resultado.getString("ultima_modificacao")));
				produtoVO.setValor((resultado.getFloat("valor") + "").replace(".", ","));
				produtoVO.setCodAlternativo(resultado.getString("codAlternativo"));
				
				produtos.add(produtoVO);
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

	public int getQtdEstoque(String codigo) {
		String comandoSQL = "SELECT quantidade FROM Produto WHERE codigo = '"
				+ codigo
				+ "';";
		
		int qtd = 0;

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next()) {
				qtd = resultado.getInt("quantidade");
			}

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return qtd;
	}

	public boolean excluiProduto(String codigo) {
		PedidoDAO ped = new PedidoDAO();
		ProducaoDAO prod = new ProducaoDAO();
		OrcamentoDAO o = new OrcamentoDAO();
		
		ped.removeProdutoPedidos(getIdProduto(codigo));
		prod.removeProdutoProducoes(getIdProduto(codigo));
		o.removeProdutoOrcamentos(getIdProduto(codigo));
		
		String comandoSQL = "DELETE FROM Produto WHERE codigo = '"
				+ codigo
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0) {
				return true;
			}

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return false;
	}

	public int getIdNovoProduto() {
		String comandoSQL = "SELECT MAX(id) FROM Produto;";
		
		int id = 0;

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next()) {
				id = resultado.getInt(1) + 1;
			}

			comando.close();
			resultado.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return id;
	}
	
	public String getDescProduto(int id) {
		String comandoSQL = "SELECT descricao FROM Produto WHERE id = "
				+ id
				+ ";";
		
		String desc = null;

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next()) {
				desc = resultado.getString("descricao");
			}

			comando.close();
			resultado.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return desc;
	}
	
//	public boolean alteraQuantidade(String codigo, int qtd) {
//		String comandoSQL = "UPDATE Produto SET quantidade = quantidade + ("
//				+ qtd
//				+ ") WHERE codigo = '"
//				+ codigo
//				+ "';";
//		
//		try {
//			Statement comando = ConexaoBD.getConexaoBD().createStatement();
//			int resultado = comando.executeUpdate(comandoSQL);
//
//			if(resultado != 0) {
//				return true;
//			}
//
//			comando.close();
//
//		}catch (SQLException e) {
//			System.err.println("Erro ao realizar conexao com o banco "
//					+ "verifique a url de conexão");
//			e.printStackTrace();
//		}
//
//		return false;
//	}
	
	public boolean alteraLocal(int id) {
		String comandoSQL = "UPDATE Produto SET idLocal = 0 WHERE idLocal = "
				+ id
				+ ";";
		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0) {
				return true;
			}

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return false;
	}
	
	public String transformaDateString(String dataBanco) {
		Date data = new Date();
		SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
					
		try {
			data = formatoBanco.parse(dataBanco);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "");
		}
		
		SimpleDateFormat formatoUsual = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataString = formatoUsual.format(data); 
		
		return dataString;
	}

	public String getLocalProduto(String codigoProduto) {
		String comandoSQL = "SELECT Localizacao.descricao FROM Produto, Localizacao WHERE idLocal = Localizacao.id AND codigo = '"
				+ codigoProduto
				+ "';";
		
		String local = null;

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next()) {
				local = resultado.getString("descricao");
			}

			comando.close();
			resultado.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}
		
		return local;
	}

	public String getRevisaoProduto(String codigoProduto) {
		String comandoSQL = "SELECT revisao FROM Produto WHERE codigo = '"
				+ codigoProduto
				+ "';";
		
		String rev = null;

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next()) {
				rev = resultado.getString("revisao");
			}

			comando.close();
			resultado.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}
		
		return rev;
	}	
}
