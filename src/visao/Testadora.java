package visao;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.Region;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import modelo.dao.ConexaoBD;
import modelo.dao.EmpresaDAO;
import modelo.dao.PedidoDAO;
import modelo.dao.ProdutoDAO;
import modelo.vo.EmpresaVO;
import modelo.vo.ItensPedidoVO;
import modelo.vo.PedidoVO;
import modelo.vo.ProdutoVO;

public class Testadora {

	public static void main(String[] args) {
		/*
		BuscaArquivo b = new BuscaArquivo();
		ArrayList<String> lista = b.buscarArquivoPorNome("0004-031-20045", "D:/Todos desenhos da Lumanfer");

		System.out.println(lista.get(0));

		try {
			Runtime.getRuntime().exec("explorer " + lista.get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */



		/*
		int cont = 1;

		ArrayList<ProdutoVO> produtos = new ArrayList<ProdutoVO>();
		ProdutoVO p;

		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://D:/CADASTRO DE PRODUTOS - 2021/CADASTRO DE PRODUTOS CLIENTES/banco.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				e.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT codigo, revisão, descrição, valor FROM marelAntigo ORDER BY codigo;");

			while(resultado.next()) {
				p = new ProdutoVO();
				p.setCodigo(resultado.getString("codigo"));
				p.setDescricao(resultado.getString("descrição"));
				p.setRevisao(resultado.getString("revisão"));
				p.setObservacao("");
				p.setQtd(0);
				p.setUltima_modificacao("2021-04-16");
				p.setLocalizacao("0");

				String descricao = p.getDescricao();
				String codAlternativo = "";

				int posicaoTraco = descricao.indexOf("-");

				if(descricao.contains("CÓD.")) {
					codAlternativo = descricao.substring(posicaoTraco - 7, posicaoTraco);
					codAlternativo = codAlternativo.replace(".", "");
					codAlternativo = codAlternativo.replace("D", "");
					codAlternativo = codAlternativo.trim();

					Locale localeBR = new Locale("pt","BR");
					NumberFormat numberFormat = NumberFormat.getNumberInstance(localeBR);

					p.setCodAlternativo(numberFormat.format(Integer.parseInt(codAlternativo)));	
				}else
					p.setCodAlternativo("");	

				p.setValor(resultado.getString("valor"));

				produtos.add(p);


			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException e) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			e.printStackTrace();
		}

		ProdutoDAO prod = new ProdutoDAO();

		for(ProdutoVO i : produtos) {
			prod.adicionaProduto(i);
			System.out.println(cont);
			cont++;
		}
		 */



		//		CopiaXLS c = new CopiaXLS();
		//		try {
		//			c.copy("E:/modelo.xlsx", "C:/Users/Usuario/Desktop/teste.xlsx");
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}



		//		try {     
		//			HSSFWorkbook documento = new HSSFWorkbook(new FileInputStream ("C:/Users/Usuario/Desktop/modelo.xls"));
		//			HSSFSheet folha = documento.getSheetAt(0);
		//
		//			HSSFRow linha = folha.getRow(5);
		//			HSSFCell coluna = linha.getCell(5);
		//			
		//			System.out.println(coluna.getStringCellValue());
		//
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}



		//		for (RelatorioVO relat : itens) {
		//			row = sheet.createRow(rownum++);
		//			cellnum = 0;
		//			cellMes = row.createCell(cellnum++);
		//			cellMes.setCellValue(relat.getMes());
		//			cellValor = row.createCell(cellnum++);
		//			cellValor.setCellValue(relat.getValor());
		//		}

		/*
		int numOrcamento = 6033;
		String pedido = "12345";
		String cliente = "Accel";
		String contato = "Ueverton";
		String valorProposta = "teste";
		String condPagamento = "teste";
		String prazoEntrega = "10/04/2021";
		String obs = "Teste";
		ArrayList<ItensPedidoVO> itens = new ArrayList<ItensPedidoVO>();

		ItensPedidoVO i = new ItensPedidoVO();
		i.setCodigoProduto("12345");
		i.setDescricaoProduto("Teste de produto 1");
		i.setQtd(100);
		i.setValorUnid(10F);
		i.setValorTot(i.getQtd() * i.getValorUnid());
		itens.add(i);

		i = new ItensPedidoVO();
		i.setCodigoProduto("54321");
		i.setDescricaoProduto("Teste de produto 2");
		i.setQtd(250);
		i.setValorUnid(15.5F);
		i.setValorTot(i.getQtd() * i.getValorUnid());
		itens.add(i);

		i = new ItensPedidoVO();
		i.setCodigoProduto("54321");
		i.setDescricaoProduto("Teste de produto 2");
		i.setQtd(250);
		i.setValorUnid(15.5F);
		i.setValorTot(i.getQtd() * i.getValorUnid());
		itens.add(i);

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream ("C:/Users/Usuario/Desktop/modelo.xls"));
			HSSFSheet sheet = workbook.getSheetAt(0);

			sheet.shiftRows(10, 14, itens.size());

			Row row = null;

			//estilo de dados produtos
			HSSFFont font = workbook.createFont();
			font.setFontName("Calibri");
			font.setFontHeightInPoints((short) 8);

			CellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			style.setFont(font);

			//estilo de dados titulo 1 produtos
			HSSFFont fontProd1 = workbook.createFont();
			fontProd1.setFontName("Calibri");
			fontProd1.setFontHeightInPoints((short) 8);

			CellStyle styleProd1 = workbook.createCellStyle();
			styleProd1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			DataFormat format = workbook.createDataFormat();
			styleProd1.setDataFormat(format.getFormat("R$ #.##0;-R$ #.##0"));
			styleProd1.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			styleProd1.setFont(fontProd1);

			//estilo de dados orcamento text
			HSSFFont fontOrcText = workbook.createFont();
			fontOrcText.setFontName("Calibri");
			fontOrcText.setFontHeightInPoints((short) 15);

			CellStyle styleOrcText = workbook.createCellStyle();
			styleOrcText.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleOrcText.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			styleOrcText.setFont(fontOrcText);

			//estilo de dados orcamento num
			HSSFFont fontOrcNum = workbook.createFont();
			fontOrcNum.setFontName("Calibri");
			fontOrcNum.setFontHeightInPoints((short) 15);

			CellStyle styleOrcNum = workbook.createCellStyle();
			styleOrcNum.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			styleOrcNum.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			styleOrcNum.setFont(fontOrcNum);

			//estilo de dados descricao do orcamento
			HSSFFont fontDados = workbook.createFont();
			fontDados.setFontName("Calibri");
			fontDados.setFontHeightInPoints((short) 8);

			CellStyle styleDados = workbook.createCellStyle();
			styleDados.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			styleDados.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			styleDados.setFont(fontDados);

			//estilo de dados obs do orcamento
			HSSFFont fontObs = workbook.createFont();
			fontObs.setFontName("Calibri");
			fontObs.setFontHeightInPoints((short) 10);

			CellStyle styleObs = workbook.createCellStyle();
			styleObs.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			styleObs.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			styleObs.setFont(fontObs);

			//estilo de dados obs do orcamento
			HSSFFont fontTextTot = workbook.createFont();
			fontTextTot.setFontName("Calibri");
			fontTextTot.setFontHeightInPoints((short) 10);

			CellStyle styleTextTot = workbook.createCellStyle();
			styleTextTot.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleTextTot.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleTextTot.setFont(fontTextTot);

			//estilo de dados obs do orcamento
			HSSFFont fontNumTot = workbook.createFont();
			fontNumTot.setFontName("Calibri");
			fontNumTot.setFontHeightInPoints((short) 10);

			CellStyle styleNumTot = workbook.createCellStyle();
			styleNumTot.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			styleNumTot.setDataFormat(format.getFormat("R$ #.##0;-R$ #.##0"));
			styleNumTot.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleNumTot.setFont(fontNumTot);

			//linha 6
			row = sheet.createRow(5);

			row.createCell(0).setCellValue("PEDIDO     :");			
			row.createCell(1).setCellValue(pedido);
			row.createCell(3).setCellValue("ORÇAMENTO");
			row.createCell(5).setCellValue(numOrcamento);

			row.getCell(0).setCellStyle(styleDados);
			row.getCell(1).setCellStyle(styleDados);

			row.getCell(3).setCellStyle(styleOrcText);

			row.getCell(5).setCellStyle(styleOrcNum);

			//linha 7
			row = sheet.createRow(6);

			row.createCell(0).setCellValue("CLIENTE     :");
			row.createCell(1).setCellValue(cliente.toUpperCase());

			row.getCell(0).setCellStyle(styleDados);
			row.getCell(1).setCellStyle(styleDados);

			//linha 8
			row = sheet.createRow(7);

			row.createCell(0).setCellValue("CONTATO :");
			row.createCell(1).setCellValue(contato.toUpperCase());

			row.getCell(0).setCellStyle(styleDados);
			row.getCell(1).setCellStyle(styleDados);

			//linhas produtos

			int cont = 10;
			int pLinha = cont + 1;
			int ultLinha = 0;
			for(ItensPedidoVO p : itens) {
				row = sheet.createRow(cont);

				row.createCell(0).setCellValue(cont - 9);
				row.createCell(1).setCellValue(p.getCodigoProduto());
				sheet.addMergedRegion(new CellRangeAddress(cont, cont, 2, 5));
				row.createCell(2).setCellValue(p.getDescricaoProduto());
				row.createCell(6).setCellValue(p.getQtd());
				row.createCell(7).setCellValue(p.getValorUnid());				
				row.createCell(10).setCellFormula("H" + (cont + 1) + "*" + "G" + (cont + 1));

				row.getCell(0).setCellStyle(styleDados);
				row.getCell(1).setCellStyle(styleDados);
				row.getCell(2).setCellStyle(styleDados);

				row.getCell(6).setCellStyle(style);
				row.getCell(7).setCellStyle(styleProd1);
				row.getCell(10).setCellStyle(styleProd1);

				cont++;			
			}

			ultLinha = cont;

			cont += 1;

			//linha
			row = sheet.createRow(cont);

			row.createCell(0).setCellValue("VAL. PROPOSTA     :");
			row.createCell(2).setCellValue(valorProposta);

			row.getCell(0).setCellStyle(styleObs);
			row.getCell(2).setCellStyle(styleObs);

			cont += 1;

			//linha
			row = sheet.createRow(cont);

			row.createCell(0).setCellValue("COND. PAG.           :");
			row.createCell(2).setCellValue(condPagamento);
			row.createCell(6).setCellValue("                              VALOR TOTAL :                  ");
			row.createCell(9).setCellFormula("SUM(K"
					+ pLinha
					+ ":K"
					+ ultLinha
					+ ")");

			row.getCell(0).setCellStyle(styleObs);
			row.getCell(2).setCellStyle(styleObs);
			row.getCell(6).setCellStyle(styleTextTot);
			row.getCell(9).setCellStyle(styleNumTot);

			cont += 1;

			//linha
			row = sheet.createRow(cont);

			row.createCell(0).setCellValue("PRAZO ENTR.         :");
			row.createCell(2).setCellValue(prazoEntrega);

			row.getCell(0).setCellStyle(styleObs);
			row.getCell(2).setCellStyle(styleObs);

			cont += 1;

			//linha
			row = sheet.createRow(cont);

			row.createCell(0).setCellValue("OBS.                       :");
			row.createCell(2).setCellValue(obs);

			row.getCell(0).setCellStyle(styleObs);
			row.getCell(2).setCellStyle(styleObs);

			FileOutputStream out = new FileOutputStream(new File("C:/Users/Usuario/Desktop/modeloCriado.xls"));

			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo não encontrado!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro na edição do arquivo!");
		}
		 */


		/*
		String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

		int cont = 0;
		int id = 1;

		for(String l : letras) {
			cont++;

			for(cont = 1;cont <=5;cont++) {
				String comandoSQL = "INSERT INTO Localizacao (id, descricao) VALUES ("+id+++", '" + l + "-0" + cont + "');";

				System.out.println(comandoSQL);
			}
		}
		 */

		/*
		String valor = "";

		float numero = 100.99F;

		valor = new DecimalFormat("0000").format((int) numero);

		int dec = (int) (numero * Math.pow(10.0, 2) - ((int) numero) * Math.pow(10.0, 2));

		valor += "." + dec;

		if(valor.length() == 6)
			valor += 0;

		System.out.println(valor);
		 */

		/*
		String senha = new String("admin");
		String senhaEncriptografada = Base64.getEncoder().encodeToString(senha.getBytes());

		System.out.println(senhaEncriptografada);

		senha = new String(Base64.getDecoder().decode(senhaEncriptografada));

		System.out.println(senha);
		 */

		/*
		boolean ehNumero = true;

		String numero = ("1,7").replace(",", "");
		numero = numero.replace(".", "");

		try {
			float n = Float.parseFloat(numero);
		}catch(NumberFormatException e){
			ehNumero = false;
		}

		System.out.println(ehNumero);
		 */

		/*
		GeraGrafico l = new GeraGrafico();
		l.setSize(1400, 850);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setResizable(true);
		l.setLocationRelativeTo(null);
		l.getContentPane().setBackground(Color.WHITE);
		l.setVisible(true);
		 */

		/*
		ProdutoDAO prod = new ProdutoDAO();
		ProdutoVO p;

		for(int qtd = 1; qtd <= 500; qtd++) {
			Random r = new Random();
			p = new ProdutoVO();
			p.setCodigo("Prod" + prod.getIdNovoProduto());
			p.setDescricao("Produto " + prod.getIdNovoProduto());
			p.setUltima_modificacao("2021-04-06");
			p.setObservacao("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			p.setLocalizacao(r.nextInt(75) + 1 + "");
			p.setQtd(r.nextInt(100));
			p.setRevisao("ABC");
			p.setValor(r.nextInt(200) + "");

			prod.adicionaProduto(p);
		}

		 */

		/*
		Random r = new Random();
		PedidoVO p;
		ArrayList<ItensPedidoVO> itens = new ArrayList<ItensPedidoVO>();
		ItensPedidoVO item;

		ProdutoDAO prod = new ProdutoDAO();
		PedidoDAO ped = new PedidoDAO();

		int qtd = 1;

		for(int id = 422; id <= 1500;id++) {
			p = new PedidoVO();

			p.setId(id + "");
			p.setNumeroNota(r.nextInt(22000) + "");
			DecimalFormat d = new DecimalFormat("00");
			p.setDataEmissao("2021-"+d.format(r.nextInt(11)+1)+"-" + d.format(r.nextInt(28) + 1));
			p.setDataEntrega("2021-"+d.format(r.nextInt(11)+1)+"-" + d.format(r.nextInt(28) + 1));
			p.setIdEmpresa(r.nextInt(13) + 1);


			for(int cont = 1; cont <= r.nextInt(49) + 1; cont++) {
				item = new ItensPedidoVO();
				item.setIdProduto(r.nextInt(3590) + 15);
				item.setIdPedido(p.getId());
				item.setDataEntrega("2021-04-20");
				item.setDescricaoProduto(prod.getDescProduto(item.getIdProduto()));
				item.setStatus(true);
				item.setQtd(r.nextInt(50) + 1);

				itens.add(item);
			}

			System.out.println("Pedido: "+qtd);
			ped.adicionaPedido(p, itens);
			itens.clear();
			qtd += 1;
		}

		 */

		//Sobrescrever banco 

		//Empresas
		/*
		int cont = 1;

		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://D:/Controle Estoque/ControleEstoque/Lumanfer.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT descricao FROM empresa ORDER BY id;");

			while(resultado.next()) {
				System.out.println("INSERT INTO empresa (id, descricao, cnpj, telefone) VALUES ("
						+ cont++
						+ ", '"
						+ resultado.getString(1)
						+ "', '', '');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		 */

		//Locais 
		/*
		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://C:\\Users\\Lumanfer Ideapad 01\\Desktop\\Controle Estoque\\banco\\Produtos.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT id, descricao FROM localizacao ORDER BY id;");

			while(resultado.next()) {
				System.out.println("INSERT INTO localizacao (id, descricao) VALUES ("
						+ resultado.getInt(1)
						+ ", '"
						+ resultado.getString(2)
						+ "');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		 */

		//produtos
		/*
		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://C:\\Users\\Lumanfer Ideapad 01\\Desktop\\Controle Estoque\\banco\\produtos.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			String comandoSQL = "SELECT id, codigo, codAlternativo, descricao, quantidade, valor, idLocal, revisao, ultima_modificacao, observacao FROM produto WHERE id BETWEEN 3601 AND 4000 AND NOT valor = '44.5' ORDER BY id;";

			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT id, codigo, codAlternativo, descricao, quantidade, valor, idLocal, revisao, ultima_modificacao, observacao FROM produto WHERE valor = '44.5' ORDER BY id;");

			while(resultado.next()) {

				Date data = new Date();
				SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");

				try {
					data = formatoBanco.parse(resultado.getString(9));
				} catch (ParseException e) {

				}

				SimpleDateFormat formatoUsual = new SimpleDateFormat("yyyy-MM-dd");

				String dataString = formatoUsual.format(data); 

				System.out.println("INSERT INTO produto (id, codigo, codAlternativo, descricao, quantidade, valor, idLocal, revisao, ultima_modificacao, observacao) VALUES ("
						+ resultado.getInt(1)
						+ ", '"
						+ resultado.getString(2)
						+ "', '"
						+ resultado.getString(3)
						+ "', '"
						+ resultado.getString(4)
						+ "', "
						+ resultado.getInt(5)
						+ ", ('"
						+ resultado.getString(6)
						+ "'), "
						+ resultado.getInt(7)
						+ ", '"
						+ resultado.getString(8)
						+ "', '"
						+ dataString
						+ "', '"
						+ resultado.getString(10)
						+ "');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		 */


		//PEDIDOS
		/*
		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://C:\\Users\\Lumanfer Ideapad 01\\Desktop\\Controle Estoque\\banco\\pedidos.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT * FROM pedido ORDER BY id;");

			while(resultado.next()) {

				Date data = new Date();
				SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");

				try {
					data = formatoBanco.parse(resultado.getString(3));
				} catch (ParseException e) {

				}

				SimpleDateFormat formatoUsual = new SimpleDateFormat("yyyy-MM-dd");

				String dataEmissao = formatoUsual.format(data); 

				try {
					data = formatoBanco.parse(resultado.getString(5));
				} catch (ParseException e) {

				}

				String dataEntrega = formatoUsual.format(data); 

				System.out.println("INSERT INTO pedido (id, idOrcamento, dataEmissao, idEmpresa, stat, dataEntrega, prodPronto, porcent) VALUES ('"
						+ resultado.getString("id")
						+ "', "
						+ resultado.getInt("idOrcamento")
						+ ", '"
						+ dataEmissao
						+ "', "
						+ resultado.getInt("idEmpresa")
						+ ", '"
						+ resultado.getString("status")
						+ "', '"
						+ dataEntrega
						+ "', '"
						+ resultado.getString("prodPronto")
						+ "', '"
						+ resultado.getString("porcent")
						+ "');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		/*

		//ORCAMENTOS
		/*
		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://C:\\Users\\Lumanfer Ideapad 01\\Desktop\\Controle Estoque\\banco\\orcamentos.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT * FROM orcamento ORDER BY id;");

			while(resultado.next()) {

				Date data = new Date();
				SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");

				try {
					data = formatoBanco.parse(resultado.getString(2));
				} catch (ParseException e) {

				}

				SimpleDateFormat formatoUsual = new SimpleDateFormat("yyyy-MM-dd");

				String dataEmissao = formatoUsual.format(data); 

				System.out.println("INSERT INTO orcamento (id, dataEmissao, idEmpresa, cliente, condPagamento, obs, valor) VALUES ("
						+ resultado.getString("id")
						+ ", '"
						+ dataEmissao
						+ "', "
						+ resultado.getInt("idEmpresa")
						+ ", '"
						+ resultado.getString("cliente")
						+ "', '"
						+ resultado.getString("condPagamento")
						+ "', '"
						+ resultado.getString("obs")
						+ "', '"
						+ "17/05/2021"
						+ "');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		
		*/
		
		//ITENSPEDIDO
		/*
		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://C:\\Users\\Lumanfer Ideapad 01\\Desktop\\Controle Estoque\\banco\\pedidos.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT * FROM itenspedido;");

			while(resultado.next()) {

				System.out.println("INSERT INTO ItensPedido (idProduto, idPedido, qtd, stat, codAlternativo, descricao) VALUES ("
						+ resultado.getInt("idProduto")
						+ ", '"
						+ resultado.getString("idPedido")
						+ "', "
						+ resultado.getInt("qtd")
						+ ", '"
						+ resultado.getString("status")
						+ "', '"
						+ resultado.getString("codAlternativo")
						+ "', '"
						+ resultado.getString("descricao")
						+ "');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		*/
		
		//ITENSORCAMENTO
		/*
		Connection conexao = null;

		String databaseURL = "jdbc:ucanaccess://C:\\Users\\Lumanfer Ideapad 01\\Desktop\\Controle Estoque\\banco\\orcamentos.accdb";

		if( conexao == null)
		{	
			try {
				conexao = DriverManager.getConnection(databaseURL);

			}		
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao realizar conexao com o banco \n" + 
						"verifique a url de conexão");
				ex.printStackTrace();
			}
		}

		try {
			Statement comando = conexao.createStatement();
			ResultSet resultado = comando.executeQuery("SELECT * FROM itensorcamento;");

			while(resultado.next()) {

				System.out.println("INSERT INTO ItensOrcamento (idProduto, idOrcamento, qtd, codAlternativo, descricao, valor) VALUES ("
						+ resultado.getInt("idProduto")
						+ ", "
						+ resultado.getInt("idOrcamento")
						+ ", "
						+ resultado.getInt("qtd")
						+ ", '"
						+ resultado.getString("codAlternativo")
						+ "', '"
						+ resultado.getString("descricao")
						+ "', '"
						+ resultado.getString("valor").replace(",", ".")
						+ "');");
			}

			resultado.close();
			comando.close();
			conexao.close();

		}catch (SQLException ex) {
			System.err.println("Erro ao realizar conexao com o banco "
					+ "verifique a url de conexão");
			ex.printStackTrace();
		}
		*/
		
		Locale localeBR = new Locale("pt","BR");
		NumberFormat numberFormat = NumberFormat.getNumberInstance(localeBR);

		System.out.println(numberFormat.format(950));

	}
	
	

}
