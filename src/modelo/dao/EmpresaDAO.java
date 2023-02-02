package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.vo.EmpresaVO;

public class EmpresaDAO {
	public ArrayList<EmpresaVO> getEmpresas(String codigo, String desc) {
		EmpresaVO empresaVO;
		ArrayList<EmpresaVO> empresas = new ArrayList<EmpresaVO>();

		String comandoSQL = "SELECT id, descricao FROM Empresa WHERE id LIKE '"
				+ codigo
				+ "%' AND descricao LIKE '"
				+ desc
				+ "%' ORDER BY id;";
		
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				empresaVO = new EmpresaVO();
				empresaVO.setId(resultado.getInt("id"));
				empresaVO.setDescricao(resultado.getString("descricao"));

				empresas.add(empresaVO);
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

		return empresas;
	}

	public int getQtdEmpresas() {
		int qtd = 0;

		String comandoSQL = "SELECT COUNT(*) FROM Empresa;";

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
	
	public int getIdNovoEmpresa() {
		int id = 0;

		String comandoSQL = "SELECT (MAX(id) + 1) FROM Empresa;";

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

	public boolean adicionaEmpresa(EmpresaVO empresa) {
		boolean adicionado = false;

		String comandoSQL = "INSERT INTO Empresa (id, descricao, cnpj, telefone) VALUES ( "
				+ getIdNovoEmpresa()
				+ ",'"
				+ empresa.getDescricao()
				+ "', '"
				+ empresa.getCnpj()
				+ "', '"
				+ empresa.getTelefone()
				+ "');";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0)
				adicionado = true;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return adicionado;
	}

	public boolean removeEmpresa(String codigo) {
		boolean removido = false;

		String comandoSQL = "DELETE FROM Empresa WHERE id = "
				+ codigo
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0)
				removido = true;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return removido;
	}

	public EmpresaVO getEmpresa(String id) {
		EmpresaVO empresaVO = null;

		String comandoSQL = "SELECT * FROM Empresa WHERE id = "
				+ id
				+ ";";
		
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			comando = ConexaoBD.getConexaoBD().createStatement();
			resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				empresaVO = new EmpresaVO();
				
				empresaVO.setId(resultado.getInt("id"));
				empresaVO.setDescricao(resultado.getString("descricao"));
				empresaVO.setCnpj(resultado.getString("cnpj"));
				empresaVO.setTelefone(resultado.getString("telefone"));
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

		return empresaVO;
	}

	public boolean atualizaEmpresa(EmpresaVO empresa) {
		boolean alterado = false;

		String comandoSQL = " UPDATE Empresa SET descricao = '"
				+ empresa.getDescricao()
				+ "', cnpj = '"
				+ empresa.getCnpj()
				+ "', telefone = '"
				+ empresa.getTelefone()
				+ "' WHERE id = "
				+ empresa.getId()
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado != 0)
				alterado = true;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return alterado;
	}
}
