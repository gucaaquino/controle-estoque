package visao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import controle.LumanferControle;
import modelo.vo.ItensPedidoVO;
import modelo.vo.PedidoVO;

public class GerarPDFPedidoVisao {
	
	private String caminho;
	
	private ArrayList<ItensPedidoVO> itensPedido;
	
	private LumanferControle controle;
	
	private PedidoVO pedidoVO;
	
	public GerarPDFPedidoVisao(String caminho, PedidoVO pedidoVO, ArrayList<ItensPedidoVO> itensPedido, LumanferControle controle) {
		
		this.caminho = caminho;
		this.itensPedido = itensPedido;
		this.pedidoVO = pedidoVO;
		this.controle = controle;
		
	}
	
	public boolean montaPDF() {
		Document document = new Document();

		try {
			PdfWriter pdf =  PdfWriter.getInstance(document, new FileOutputStream(caminho));
			
			// Abre documento
			document.open();
			
			document.add(new Paragraph("\n\n"));
			
			Paragraph pTitulo = new Paragraph(new Phrase(20F , "Pedido: " + pedidoVO.getId() + " (" + pedidoVO.getEmpresa() + ")", new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK)));
            pTitulo.setAlignment(Element.ALIGN_CENTER);
            document.add( pTitulo );
			
			Font fontData = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
            Paragraph pData = new Paragraph(new Phrase(14F , "Data de entrega: " + transformaDateString(pedidoVO.getDataEntrega()), fontData));
            pData.setAlignment(Element.ALIGN_CENTER);
            document.add( pData );
            
            Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
            Paragraph pOrcc = new Paragraph(new Phrase(14F , "Orçamento: " + pedidoVO.getOrcamento(), font));
            pOrcc.setAlignment(Element.ALIGN_CENTER);
            document.add( pOrcc );
			
			document.add(new Paragraph("\n\n"));
			
			PdfPTable table = criarCabecalho();
			
			preencherDados(document, table, itensPedido, controle);
			
			// Encerra documento
			document.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	public static PdfPTable criarCabecalho()
			throws DocumentException {
		
		PdfPTable table = new PdfPTable(new float[] { 2, 4, 1, 5, 1, 5 });
		
		table.setWidthPercentage(110);
		
		Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
		
		PdfPCell celulaId = new PdfPCell(new Phrase("CÓD. ALT", font));
		celulaId.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell celulaCodigo = new PdfPCell(new Phrase("CÓD. DESENHO", font));
		celulaCodigo.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell celulaQtd = new PdfPCell(new Phrase("DESCRIÇÃO", font));
		celulaQtd.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell celulaRev = new PdfPCell(new Phrase("REV", font));
		celulaRev.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell celulaUnid = new PdfPCell(new Phrase("QTD", font));
		celulaUnid.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell celulaDesc = new PdfPCell(new Phrase("OBS", font));
		celulaDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
		

		table.addCell(celulaId);
		table.addCell(celulaCodigo);
		table.addCell(celulaRev);
		table.addCell(celulaQtd);
		table.addCell(celulaUnid);
		table.addCell(celulaDesc);

		return table;
	}

	public static void preencherDados(Document document, PdfPTable table, ArrayList<ItensPedidoVO> itensPedido, LumanferControle controle) throws DocumentException {
		if (document.isOpen()) {
			
			DecimalFormat d = new DecimalFormat("00");
			
			Font font = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font font2 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.RED);
			Font font3 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
			
			for (ItensPedidoVO i : itensPedido) {
				
				PdfPCell celula1 = new PdfPCell(new Phrase("\n" + i.getCodAlternativo(), font));
				PdfPCell celula2 = new PdfPCell(new Phrase("\n" + i.getCodigoProduto(), font));
				PdfPCell celula3 = new PdfPCell(new Phrase("\n" + i.getDescricaoProduto(), font));
				PdfPCell celula4 = new PdfPCell(new Phrase("\n" + d.format(i.getQtd()), font3));
				PdfPCell celula5 = new PdfPCell(new Phrase("\n" + controle.getRevisaoProduto(i.getCodigoProduto()), font2));
				PdfPCell celula6 = new PdfPCell(new Phrase("\n\n"));
				
				celula4.setHorizontalAlignment(Element.ALIGN_CENTER);
				celula5.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(celula1);
				table.addCell(celula2);
				table.addCell(celula5);
				table.addCell(celula3);
				table.addCell(celula4);
				table.addCell(celula6);
				
			}

			document.add(table);
		}
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
