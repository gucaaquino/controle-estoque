package visao;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import controle.LumanferControle;
import modelo.vo.EmpresaVO;

public class GeraGraficoRelatVendasVisao extends JFrame{	
	private LumanferControle controle;
	
	private ArrayList<EmpresaVO> empresas;
	
	private String dataInicio;
	private String dataFim;
	
	public GeraGraficoRelatVendasVisao(String dataInicio, String dataFim, LumanferControle controle) {
		super("Gráfico Vendas");
		
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.controle = controle;
		
		gerarGrafico();
	}
	
	private void gerarGrafico() {
		
		empresas = controle.getRelatorioVendasEmpresas(dataInicio, dataFim);
		
		DefaultPieDataset dataset = new DefaultPieDataset();

		int qtd = 0;
		String empresa = "";
		
		for(EmpresaVO e : empresas) {
			qtd = e.getId();
			empresa = e.getDescricao();
			
			dataset.setValue(empresa , qtd);
		}
		
		JFreeChart grafico = ChartFactory.createPieChart("", dataset, true, true, false);
		
		ChartPanel painel = new ChartPanel(grafico);
		
		add(painel);
	}
}
