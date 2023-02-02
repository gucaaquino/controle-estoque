package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import modelo.vo.EmpresaVO;
import modelo.vo.ProdutoVO;
import modelo.vo.RelatorioVO;

public class RelatorioDAO {

	public ArrayList<RelatorioVO> getVendas(String dataInicio, String dataFim) {
		
		ProdutoDAO p = new ProdutoDAO();

		ArrayList<RelatorioVO> arrayRelatorios = new ArrayList<RelatorioVO>();

		RelatorioVO relatorio = null;
		ProdutoVO produto = null;

		int qtdTotal = 0;

		String comandoSQL = "SELECT idProduto, Sum(ItensPedido.qtd) as soma FROM ItensPedido, Pedido WHERE ItensPedido.idPedido = Pedido.id AND Pedido.dataEmissao Between #"
				+ dataInicio
				+ "# AND #"
				+ dataFim
				+ "# GROUP BY idProduto ORDER BY Sum(ItensPedido.qtd) DESC;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				relatorio = new RelatorioVO();
				produto = new ProdutoVO();

				produto = p.getProduto(p.getCodigoProduto(resultado.getInt("idProduto")));

				relatorio.setCodigo(produto.getCodigo());
				relatorio.setQtd(resultado.getInt("soma"));
				relatorio.setValorUnid(produto.getValor());

				float valorUnid = Float.parseFloat(produto.getValor());
				relatorio.setValorTotal("" + relatorio.getQtd() * valorUnid);
				relatorio.setDescricao(produto.getDescricao());

				qtdTotal += resultado.getInt("soma");

				arrayRelatorios.add(relatorio);
			}
			
			float conta = 0f;

			for(RelatorioVO r : arrayRelatorios) {
				String porcent = "";
				DecimalFormat formatter = new DecimalFormat("#.00000");
				try{
					conta = ((float)r.getQtd()/(float)qtdTotal) * 100;
					porcent = formatter.format(conta);
				}catch(Exception ex){
					System.err.println("Erro ao formatar numero: " + ex);
				}
				r.setPorcent(porcent + "%");
				
				if(conta < 1f)
					r.setPorcent("0" + r.getPorcent());
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return arrayRelatorios;
	}

	public ArrayList<EmpresaVO> getRelatorioVendasEmpresas(String dataInicio, String dataFim) {
		ArrayList<EmpresaVO> empresas = new ArrayList<EmpresaVO>();
		EmpresaVO empresa = null;
		
		String comandoSQL = "SELECT Empresa.descricao, SUM(qtd) FROM Empresa, Pedido, ItensPedido WHERE dataEmissao BETWEEN #"
				+ dataInicio
				+ "# AND #"
				+ dataFim
				+ "# AND Pedido.id = idPedido AND Empresa.id = idEmpresa GROUP BY Empresa.descricao;";
		
		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				empresa = new EmpresaVO();
				empresa.setDescricao(resultado.getString("Empresa.descricao"));
				empresa.setId(resultado.getInt(2));
				
				empresas.add(empresa);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}
		
		
		return empresas;
	}

	public ArrayList<RelatorioVO> getRelatorioABC(String dataInicio, String dataFim, String codigo) {
		ProdutoDAO p = new ProdutoDAO();

		ArrayList<RelatorioVO> arrayRelatorios = new ArrayList<RelatorioVO>();

		RelatorioVO relatorio = null;
		ProdutoVO produto = null;

		int qtdTotal = 0;
		
		String comandoSQL = "SELECT idProduto, SUM(qtd) as soma FROM Producao, ItensProducao WHERE dataProducao BETWEEN #"
				+ dataInicio
				+ "# AND #"
				+ dataFim
				+ "# AND "
				+ codigo
				+ " Producao.id = idProducao GROUP BY idProduto;";
		
		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				relatorio = new RelatorioVO();
				produto = new ProdutoVO();

				produto = p.getProduto(p.getCodigoProduto(resultado.getInt("idProduto")));

				relatorio.setCodigo(produto.getCodigo());
				relatorio.setQtd(resultado.getInt("soma"));
				relatorio.setValorUnid(produto.getValor());

				float valorUnid = Float.parseFloat(produto.getValor());
				relatorio.setValorTotal("" + relatorio.getQtd() * valorUnid);
				relatorio.setDescricao(produto.getDescricao());

				qtdTotal += resultado.getInt("soma");

				arrayRelatorios.add(relatorio);
			}

			for(RelatorioVO r : arrayRelatorios) {
				String porcent = "";
				DecimalFormat formatter = new DecimalFormat("#.00");
				try{
					porcent = formatter.format(((float)r.getQtd()/(float)qtdTotal) * 100);
				}catch(Exception ex){
					System.err.println("Erro ao formatar numero: " + ex);
				}
				r.setPorcent(porcent + "%");
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		
		return arrayRelatorios;
	}

	public ArrayList<EmpresaVO> getRelatorioABCProdutos(String codigo) {
		ArrayList<EmpresaVO> empresas = new ArrayList<EmpresaVO>();
		EmpresaVO empresa = null;
		
		for(int mes = 1; mes <= 12; mes++) {
			empresa = new EmpresaVO();
			empresa.setDescricao(mes + "");
			empresa.setId(0);
			
			empresas.add(empresa);
		}
		
		String comandoSQL = "";
		
		if(codigo != null)
			comandoSQL = "SELECT idProduto, SUM(qtd) as soma, Month(dataProducao) as mes FROM Producao, ItensProducao WHERE Producao.id = idProducao AND idProduto = "
					+ codigo
					+ " GROUP BY Month(dataProducao), idProduto;";
		else
			comandoSQL = "SELECT SUM(qtd) as soma, Month(dataProducao) as mes FROM Producao, ItensProducao WHERE Producao.id = idProducao GROUP BY Month(dataProducao);";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				for(EmpresaVO e : empresas) {
					if(e.getDescricao().equals(resultado.getInt("mes") + ""))
						e.setId(resultado.getInt("soma"));
				}
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}
		
		
		return empresas;
	}
}
