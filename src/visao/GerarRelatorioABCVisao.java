package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

import controle.LumanferControle;
import modelo.vo.RelatorioVO;

public class GerarRelatorioABCVisao extends JFrame{
	private JDateChooser dateInicio;
	private JDateChooser dateFim;

	private JButton buttonLimpar;
	private JButton buttonPesquisar;
	private JButton buttonVoltar;
	private JButton buttonGrafico;
	private JButton buttonPesqProd;

	private ImageIcon pesquisaIcon;
	private ImageIcon limparIcon;
	private ImageIcon voltarIcon;
	private ImageIcon graficoIcon;

	public JTextField textCodigo;

	private ArrayList<RelatorioVO> itensTabela;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private LumanferControle controle;

	private LumanferMenu menu;

	//construtor
	public GerarRelatorioABCVisao(LumanferControle controle, LumanferMenu menu) {
		super("Relatório curva ABC");

		this.controle = controle;
		this.menu = menu;

		try { 

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	
		} 
		catch (Exception e) 
		{ }

		criaJTable();

		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonPesquisar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						atualizaTabela();
					}
				}
				);

		buttonLimpar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						limpar();
					}
				}
				);

		buttonGrafico.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				geraGrafico();
			}
		});
		
		

		buttonPesqProd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pesquisaProduto();
			}
		});

		buttonVoltar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						fechar();
					}
				}
				);
	}

	//metodo responsavel por criar a JTable
	private void criaJTable() {
		//inicializa a JTable
		table = new JTable();

		//mas a JTable precisa de algo para manipular seus dados(inserir linha, excluir...)
		tableModel = new DefaultTableModel() {
			//por padrao o DefaultTableModel permite fazer alteracoes na JTable, por isso precisamos 
			//sobrecarregar seu metodo dizendo que nenhuma das linhas podera ser alterada
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		//definindo que o tableModel seta responsavel por manipular os dados da JTable
		table.setModel(tableModel);

		//adicionando todas as colunas da tabela
		tableModel.addColumn("Código");
		tableModel.addColumn("Descrição");
		tableModel.addColumn("Qtd Produção");
		tableModel.addColumn("Valor Unid");
		tableModel.addColumn("Valor Total");
		tableModel.addColumn("%");

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		textCodigo = new JTextField(15);

		buttonLimpar = new JButton("");
		buttonPesquisar = new JButton("");
		buttonVoltar = new JButton("");
		buttonGrafico = new JButton("");
		buttonPesqProd = new JButton("...");

		pesquisaIcon = new ImageIcon("./icones/pesquisa.png");
		limparIcon = new ImageIcon("./icones/clean.png");
		voltarIcon = new ImageIcon("./icones/de-volta.png");
		graficoIcon = new ImageIcon("./icones/graficoBarra.png");

		buttonPesquisar.setToolTipText("Pesquisar");
		buttonLimpar.setToolTipText("Limpar tabela");
		buttonVoltar.setToolTipText("Voltar a tela inicial");
		buttonGrafico.setToolTipText("Mostrar gráfico empresas");

		buttonPesquisar.setIcon(pesquisaIcon);
		buttonLimpar.setIcon(limparIcon);
		buttonVoltar.setIcon(voltarIcon);
		buttonGrafico.setIcon(graficoIcon);

		dateInicio = new JDateChooser();
		dateFim = new JDateChooser();

		dateInicio.setDate(new Date());
		dateFim.setDate(new Date());

		itensTabela = new ArrayList<RelatorioVO>();

		textCodigo.setEditable(false);
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"5dlu, p, 5dlu, 80dlu, 5dlu, p, 5dlu, 80dlu, 5dlu, p, 5dlu, 80dlu, 5dlu, p, 5dlu, p, 180dlu:grow, 5dlu",
				"5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 280dlu:grow, p, 5dlu");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.add(barraRolagem, cc.xywh(2, 4, 16, 7));

		builder.addLabel("Data início:", cc.xy(2, 2));
		builder.add(dateInicio, cc.xy(4, 2));

		builder.addLabel("Data fim:", cc.xy(6, 2));
		builder.add(dateFim, cc.xy(8, 2));

		builder.addLabel("Código:", cc.xy(10, 2));
		builder.add(textCodigo, cc.xy(12, 2));
		builder.add(buttonPesqProd, cc.xy(14, 2));

		builder.add(montaBarraBotao(), cc.xyw(16, 2, 2));

		return builder.getPanel();
	}

	private void pesquisaProduto() {
		GerenciarProdutosVisao g = new GerenciarProdutosVisao(controle, null, null, null, null, null, GerarRelatorioABCVisao.this, null, null);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);

		this.setVisible(false);
	}

	private void geraGrafico() {
		String codigo = textCodigo.getText();
		String titulo = "";

		if(codigo.equals("")) {
			codigo = null;
			titulo = "Todos Produtos";
		}else {
			titulo = codigo;
			codigo = controle.getIdProduto(codigo) + "";
		}

		GeraGraficoRelatABCVisao g = new GeraGraficoRelatABCVisao(titulo, codigo, controle);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	public void atualizaTabela() {
		tableModel.setNumRows(0);

		String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dateInicio.getDate());
		String dataFim = new SimpleDateFormat("yyyy-MM-dd").format(dateFim.getDate());

		String codigo = "";

		if(!textCodigo.getText().equals(""))
			codigo = "IdProduto = " + controle.getIdProduto(textCodigo.getText()) + " AND";

		itensTabela = controle.getRelatorioABC(dataInicio, dataFim, codigo);

		for(RelatorioVO r : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					r.getCodigo(), r.getDescricao(), r.getQtd(), r.getValorUnid(), r.getValorTotal(), r.getPorcent()
			});
		}

		int qtd = 0;
		float valorTot = 0f;

		for(RelatorioVO r : itensTabela) {
			qtd += r.getQtd();
			valorTot += Float.parseFloat(r.getValorTotal());
		}

		String valor = "R$" + valorTot;

		valor = valor.replace(".", ",");

		tableModel.addRow(new Object[] {
				"------", "------", qtd, "------", valor, "------"
		});
	}

	private void fechar() {
		GerarRelatorioABCVisao.this.dispose();

		menu.atualizaEmpresas();
		menu.atualizaTabela();
	}

	private void limpar() {
		dateInicio.setDate(new Date());
		dateFim.setDate(new Date());
		tableModel.setNumRows(0);
		itensTabela.clear();
		textCodigo.setText("");
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonPesquisar, buttonLimpar, buttonGrafico, buttonVoltar).build();	
	}
}
