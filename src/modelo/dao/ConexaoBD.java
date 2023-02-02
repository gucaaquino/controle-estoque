package modelo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	private static Connection conexao = null;

	private ConexaoBD() {

	}
	public static Connection getConexaoBD() {
		
		String caminhoBanco = "./banco/Lumanfer";
		
		Connection conexao = null;
		
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			conexao = DriverManager.getConnection(
					"jdbc:hsqldb:file:" + caminhoBanco + ";shutdown=true;hsqldb.lock_file=false","sa","");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //carrega o driver

		return conexao;
	}

	public static void closeCoxexaoBD() {

		try {
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Erro ao realizar fechamento da conexao com o banco ");
			e.printStackTrace();

		}
	}
}
