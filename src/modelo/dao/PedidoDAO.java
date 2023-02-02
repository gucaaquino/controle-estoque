package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import modelo.vo.ItensPedidoVO;
import modelo.vo.PedidoVO;

public class PedidoDAO {

	public boolean existeIdPedido(String id) {
		boolean existe = false;

		String comandoSQL = "SELECT COUNT(*) FROM Pedido WHERE id = '"
				+ id
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				if(resultado.getInt(1) != 0) {
					existe = true;
				}
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return existe;
	}

	public boolean adicionaPedido(PedidoVO pedidoVO, ArrayList<ItensPedidoVO> itensTabela) {
		boolean insercao = true;

		int qtd = 0;

		for(ItensPedidoVO i : itensTabela)
			qtd += i.getQtd();

		String comandoSQL = "INSERT INTO Pedido (id, idOrcamento, dataEmissao, idEmpresa, stat, dataEntrega, prodPronto, porcent) VALUES ('"
				+ pedidoVO.getId()
				+ "', "
				+ pedidoVO.getOrcamento()
				+ ", '"
				+ pedidoVO.getDataEmissao()
				+ "', "
				+ pedidoVO.getIdEmpresa()
				+ ", 'true', '"
				+ pedidoVO.getDataEntrega()
				+ "', '0/"
				+ qtd
				+ "', '0%');";

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

		if(!adicionaItensPedido(itensTabela, pedidoVO.getId()))
			insercao = false;

		return insercao;
	}

	public int getQtdPedidos() {
		int qtd = 0;

		String comandoSQL = "SELECT count(*) FROM Pedido;";

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

	public ArrayList<PedidoVO> getPedidos(String codigo, String empresa, String status, String limite) {
		PedidoVO pedidoVO;
		ArrayList<PedidoVO> pedidos = new ArrayList<PedidoVO>();

		Date d = new Date();
		// criar o Calendar
		Calendar cal = Calendar.getInstance();

		// setar o timestamp
		cal.setTimeInMillis(d.getTime());

		// mudar o horário para 23:59:59.999
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 0000);

		d = cal.getTime();

		String comandoSQL = "SELECT Pedido.id, idOrcamento, dataEmissao, idEmpresa, Empresa.descricao, stat, dataEntrega, prodPronto, porcent FROM Pedido, Empresa WHERE idEmpresa = Empresa.id AND Pedido.id LIKE '"
				+ codigo
				+ "%' AND Empresa.descricao LIKE '"
				+ empresa
				+ "%' AND stat LIKE '"
				+ status
				+ "%' ORDER BY dataEntrega, stat DESC "
				+ limite
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				pedidoVO = new PedidoVO();
				pedidoVO.setId(resultado.getString("Pedido.id"));
				pedidoVO.setIdEmpresa(resultado.getInt("idEmpresa"));
				pedidoVO.setEmpresa(resultado.getString("Empresa.descricao"));
				pedidoVO.setNumeroOrc(resultado.getInt("idOrcamento"));
				pedidoVO.setDataEntrega(resultado.getString("dataEntrega"));
				pedidoVO.setProdutosProntos(resultado.getString("prodPronto"));
				pedidoVO.setPorcentConclusao(resultado.getString("porcent"));

				String stat = resultado.getString("stat");

				if(stat.equals("true")) {
					if(d.equals(converteDia(pedidoVO.getDataEntrega()))) 
						stat = "ENTREGA HOJE";
					else if(d.after(converteDia(pedidoVO.getDataEntrega())))
						stat = "ATRASADO";
					else
						stat = "EM ANDAMENTO";
				}else
					stat = "FINALIZADO";

				pedidoVO.setStatus(stat);

				pedidos.add(pedidoVO);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return pedidos;
	}

	public ArrayList<String> getIdPedidos() {
		ArrayList<String> pedidos = new ArrayList<String>();

		String comandoSQL = "SELECT id FROM Pedido ORDER BY id;";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				String id = resultado.getString("id");

				pedidos.add(id);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return pedidos;
	}

	public PedidoVO getPedido(String id) {
		PedidoVO pedidoVO = null;

		String comandoSQL = "SELECT Pedido.id, idOrcamento, dataEmissao, idEmpresa, Empresa.id, Empresa.Descricao, stat, dataEntrega FROM Pedido, Empresa WHERE idEmpresa = Empresa.id AND Pedido.id = '"
				+ id
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				pedidoVO = new PedidoVO();
				pedidoVO.setId(resultado.getString("Pedido.id"));
				pedidoVO.setIdEmpresa(resultado.getInt("idEmpresa"));
				pedidoVO.setEmpresa(resultado.getString("Empresa.descricao"));
				pedidoVO.setDataEmissao(transformaDateString(resultado.getString("dataEmissao")));
				pedidoVO.setDataEntrega(transformaDateString(resultado.getString("dataEntrega")));
				pedidoVO.setNumeroOrc(resultado.getInt("idOrcamento"));
				pedidoVO.setStatus(resultado.getString("stat"));
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return pedidoVO;
	}

	public ArrayList<ItensPedidoVO> getProdutosPedido(String id) {
		ProdutoDAO p = new ProdutoDAO();
		ItensPedidoVO itemVO;
		ArrayList<ItensPedidoVO> itens = new ArrayList<ItensPedidoVO>();

		String comandoSQL = "SELECT idPedido, idProduto, ItensPedido.qtd, ItensPedido.descricao, Produto.codigo, Produto.quantidade, ItensPedido.stat, ItensPedido.codAlternativo FROM ItensPedido, Pedido, Produto WHERE idPedido = Pedido.id AND idProduto = Produto.id AND idPedido = '"
				+ id
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				itemVO = new ItensPedidoVO();
				itemVO.setIdPedido(id);
				itemVO.setCodigoProduto(resultado.getString("Produto.codigo"));
				itemVO.setDescricaoProduto(resultado.getString("ItensPedido.descricao"));
				itemVO.setIdProduto(resultado.getInt("idProduto"));
				itemVO.setQtd(resultado.getInt("ItensPedido.qtd"));
				itemVO.setQtdEstoque(resultado.getInt("Produto.quantidade"));
				itemVO.setValorUnid(p.getValorProduto(resultado.getInt("idProduto")));

				String codAlt = resultado.getString("ItensPedido.codAlternativo");

				if(codAlt == null)
					codAlt = "";

				itemVO.setCodAlternativo(codAlt);

				if(resultado.getString("ItensPedido.stat").equals("true"))
					itemVO.setStatus(true);
				else
					itemVO.setStatus(false);

				itens.add(itemVO);
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return itens;
	}

	public int getQtdProdutosPedido(String id) {
		int qtd = 0;

		String comandoSQL = "SELECT SUM(qtd) FROM ItensPedido WHERE idPedido = '"
				+ id
				+ "' GROUP BY idPedido;";

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

	public int getQtdProdutosPedidoPronto(String id) {
		int qtd = 0;

		String comandoSQL = "SELECT SUM(qtd) FROM ItensPedido WHERE idPedido = '"
				+ id
				+ "' AND stat = 'false' GROUP BY idPedido;";

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

	public boolean alteraPedido(PedidoVO p, ArrayList<ItensPedidoVO> itensTabela) {
		boolean alterar = true;

		excluiItensPedido(p.getId(), getProdutosPedido(p.getId()));

		if(!adicionaItensPedido(itensTabela, p.getId()))
			alterar = false;

		int qtdPronto = getQtdProdutosPedidoPronto(p.getId());
		int qtdTotal = getQtdProdutosPedido(p.getId());

		float conta = 0; 

		if(qtdPronto != 0 && qtdTotal != 0)
			conta = ((float) qtdPronto / (float) qtdTotal) * 100;

		p.setProdutosProntos(qtdPronto + "/" + qtdTotal);
		p.setPorcentConclusao((int)conta + "%");

		String comandoSQL = "UPDATE Pedido SET idOrcamento = "
				+ p.getOrcamento()
				+ ", dataEntrega = '"
				+ p.getDataEntrega()
				+ "', idEmpresa = "
				+ p.getIdEmpresa()
				+ ", stat = '"
				+ p.getStatus()
				+ "', prodPronto = '"
				+ p.getProdutosProntos()
				+ "', porcent = '"
				+ p.getPorcentConclusao()
				+ "' WHERE id = '"
				+ p.getId()
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			if(resultado == 0)
				alterar = false;

			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return alterar;
	}

	public boolean adicionaItensPedido(ArrayList<ItensPedidoVO> itensTabela, String idPedido) {
		String comandoSQL;
		int qtdProdutos = 0;

		for(ItensPedidoVO i : itensTabela) {
			String codAlt = i.getCodAlternativo();

			if(codAlt.equals(""))
				codAlt = "";

			comandoSQL = "INSERT INTO ItensPedido (idProduto, idPedido, qtd, descricao, stat, codAlternativo) VALUES ( "
					+ i.getIdProduto()
					+ ", '"
					+ idPedido
					+ "', "
					+ i.getQtd()
					+ ", '"
					+ i.getDescricaoProduto()
					+ "', '"
					+ i.isStatus()
					+ "', '"
					+ codAlt
					+ "');";

//			p.alteraQuantidade(i.getCodigoProduto(), -i.getQtd());

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

	public boolean excluiItensPedido(String id, ArrayList<ItensPedidoVO> itensTabela) {
//		ProdutoDAO p = new ProdutoDAO();
		String comandoSQL = "DELETE FROM ItensPedido WHERE idPedido = '"
				+ id
				+ "';";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			int resultado = comando.executeUpdate(comandoSQL);

			comando.close();

//			for(ItensPedidoVO i : itensTabela)
//				p.alteraQuantidade(i.getCodigoProduto(), i.getQtd());

			if(resultado == 0) 
				return false;

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		return true;
	}

	public boolean excluiPedido(String codigo, ArrayList<ItensPedidoVO> itensTabela) {
		excluiItensPedido(codigo, itensTabela);

		String comandoSQL = "DELETE FROM Pedido WHERE id = '"
				+ codigo
				+ "';";

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

	public boolean excluiPedidosOrcamento(int idOrc) {
		String comandoSQL = "SELECT id FROM Pedido WHERE idOrcamento = "
				+ idOrc
				+ ";";

		try {
			Statement comando = ConexaoBD.getConexaoBD().createStatement();
			ResultSet resultado = comando.executeQuery(comandoSQL);

			while(resultado.next()) {
				int id = resultado.getInt("id");
				
				excluiPedido("" + id, getProdutosPedido("" + id));
			}

			resultado.close();
			comando.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
			
			return false;
		}

		return true;
	}

	public boolean removeProdutoPedidos(int idProduto) {
		String comandoSQL = "DELETE FROM ItensPedido WHERE idProduto = "
				+ idProduto
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

	//converte String em variavel do tipo date
	public Date converteDia(String dia) {
		Date horaDate = null;
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");

		try {
			horaDate = data.parse(dia);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "");
		}

		return horaDate;
	}

	public boolean alteraOrcamentoPedido(String pedido, int numOrcamento) {
		String comandoSQL = "UPDATE Pedido SET idOrcamento = "
				+ numOrcamento
				+ " WHERE id = '"
				+ pedido
				+ "';";

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

}
