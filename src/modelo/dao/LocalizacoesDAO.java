package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.vo.LocalizacaoVO;

public class LocalizacoesDAO {

	public int getQtdLocalizacoes() {
		int qtd = 0;

		String comandoSQL = "SELECT count(*) FROM Localizacao;";

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

	public ArrayList<LocalizacaoVO> getLocalizacoes(String codigo, String desc) {
		ArrayList<LocalizacaoVO> localizacoesArray = new ArrayList<LocalizacaoVO>();
		LocalizacaoVO localizacaoVO;

		String comandoSQL = "SELECT * FROM Localizacao WHERE id LIKE '"
				+ codigo
				+ "%' AND descricao LIKE '"
				+ desc
				+ "%' ORDER BY descricao;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				localizacaoVO = new LocalizacaoVO();
				localizacaoVO.setId(resultado.getInt("id"));
				localizacaoVO.setDescricao(resultado.getString("descricao"));

				localizacoesArray.add(localizacaoVO);
			}
			
			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return localizacoesArray;
	}

	public boolean existeLocal(String descricao) {
		int qtd = 0;

		String comandoSQL = "SELECT COUNT(*) FROM Localizacao WHERE descricao = '"
				+ descricao
				+ "';";

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

		if(qtd != 0)
			return true;
		
		return false;
	}

	public boolean addLocal(String descricao) {
		int qtd = 0;

		String comandoSQL = "INSERT INTO Localizacao (id, descricao) VALUES ("
				+ getIdNovoLocal()
				+ ",'"
				+ descricao
				+ "');";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			qtd = comando.executeUpdate(comandoSQL);
			
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		if(qtd != 0)
			return true;
		
		return false;
	}
	
	public int getIdNovoLocal() {
		int id = 0;
		
		String comandoSQL = "SELECT MAX(id) + 1 FROM Localizacao;";

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

	public boolean removeLocal(int id) {
		int qtd = 0;
		
		ProdutoDAO p = new ProdutoDAO();
		p.alteraLocal(id);

		String comandoSQL = "DELETE FROM Localizacao WHERE id = "
				+ id
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			qtd = comando.executeUpdate(comandoSQL);
			
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		if(qtd != 0)
			return true;
		
		return false;
	}

	public String getIdLocal(String desc) {
		String id = null;

		String comandoSQL = "SELECT id FROM Localizacao WHERE descricao = '"
				+ desc
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);
			
			while(resultado.next())
				id = "" + resultado.getInt("id");
			
			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return id;
	}

	public boolean atualizaLocal(LocalizacaoVO l) {
		int qtd = 0;

		String comandoSQL = "UPDATE Localizacao SET descricao = '"
				+ l.getDescricao()
				+ "' WHERE id = "
				+ l.getId()
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			qtd = comando.executeUpdate(comandoSQL);
			
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		if(qtd != 0)
			return true;
		
		return false;
	}

}
