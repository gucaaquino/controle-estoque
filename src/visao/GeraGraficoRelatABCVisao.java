package visao;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import controle.LumanferControle;
import modelo.vo.EmpresaVO;

public class GeraGraficoRelatABCVisao extends JFrame{	
	private LumanferControle controle;
	
	private ArrayList<EmpresaVO> empresas;
	
	private String titulo;
	private String codigo;
	
	public GeraGraficoRelatABCVisao(String titulo, String codigo, LumanferControle controle) {
		super("Gráfico ABC");
		
		this.titulo = titulo;
		this.codigo = codigo;
		this.controle = controle;
		
		gerarGrafico();
	}
	
	private void gerarGrafico() {
		empresas = controle.getRelatorioABCProdutos(codigo);
		
		DefaultCategoryDataset barra = new DefaultCategoryDataset();
		
		int qtd = 0;
		String mes = "";

		for(EmpresaVO e : empresas) {
			qtd = e.getId();
			mes = "Mes " + e.getDescricao();
			barra.setValue(qtd, mes, "");
		}

		JFreeChart grafico = ChartFactory.createBarChart(titulo, "", "", barra, PlotOrientation.VERTICAL, true, true, false);
		
		ChartPanel painel = new ChartPanel(grafico);

		add(painel);
	}
}
