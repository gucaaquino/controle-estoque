package visao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import modelo.vo.ItensPedidoVO;
import modelo.vo.OrcamentoVO;

public class GerarExcelPedidoVisao {
	
	private OrcamentoVO orcamento;
	private String caminho;

	public GerarExcelPedidoVisao(OrcamentoVO orcamento, String caminho) {
		this.orcamento = orcamento;
		this.caminho = caminho;
	}
	
	public boolean geraExcel() {
		int numOrcamento = orcamento.getId();
		String pedido = orcamento.getPedido();
		String cliente = orcamento.getEmpresa();
		String contato = orcamento.getCliente();
		String valorProposta = orcamento.getValProposta();
		String condPagamento = orcamento.getCondPagamento();
		String prazoEntrega = "";
		String obs = orcamento.getObs();
		ArrayList<ItensPedidoVO> itens = orcamento.getItens();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream ("./modelo.xls"));
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
				DecimalFormat d = new DecimalFormat("00");
				row.createCell(6).setCellValue(d.format(p.getQtd()));
				row.createCell(7).setCellValue("R$ " + p.getValorUnid().replace(".", ","));				
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

			row.createCell(0).setCellValue("VAL. PROPOSTA    :");
			row.createCell(2).setCellValue(transformaDateString(valorProposta));
			
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
			
			row.createCell(0).setCellValue("OBS.                        :");
			row.createCell(2).setCellValue(obs);
			
			row.getCell(0).setCellStyle(styleObs);
			row.getCell(2).setCellStyle(styleObs);

			FileOutputStream out = new FileOutputStream(new File(caminho));
			
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo não encontrado!");
			
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro na edição do arquivo!");
			
			return false;
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

		SimpleDateFormat formatoUsual = new SimpleDateFormat("dd/MM/yyyy");

		String dataString = formatoUsual.format(data); 

		return dataString;
	}
}
