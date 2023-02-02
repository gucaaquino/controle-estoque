package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
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
import modelo.vo.ItensProducaoVO;
import modelo.vo.ProducaoVO;

public class VisualizarProducaoVisao extends JFrame{
	private LumanferControle controle;
	private GerenciarProducaoVisao gerenciarProducaoVisao;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private JTextField textCodigo;

	private int id;

	private JDateChooser dateProducao;
	
	private JButton buttonFechar;

	private ImageIcon iconVoltar;

	private ArrayList<ItensProducaoVO> itensTabela;

	//construtor
	public VisualizarProducaoVisao(LumanferControle controle, GerenciarProducaoVisao gerenciarProducaoVisao, int id) {
		super("Visualizar Produção");

		this.controle = controle;
		this.gerenciarProducaoVisao = gerenciarProducaoVisao;
		this.id = id;

		try { 
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	// Java Swing Nimbus

		} 
		catch (Exception e) 
		{ }

		//chama o metodo responsavel por criar a JTable 
		criaJTable();

		//chama o metodo rsponsavel por inicializar os componentes
		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});
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
		tableModel.addColumn("Qtd");

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);
		
		textCodigo = new JTextField(15);

		buttonFechar = new JButton("");

		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonFechar.setIcon(iconVoltar);

		dateProducao = new JDateChooser();
		dateProducao.setDate(new Date());

		itensTabela = new ArrayList<ItensProducaoVO>();

		textCodigo.setText("" + id);
		pesquisaProducao();
		
		textCodigo.setEditable(false);
		dateProducao.setEnabled(false);

	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:p, 5dlu, p, 5dlu, p, 5dlu, right:p, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 140dlu, 15dlu, p, 10dlu:grow");

		layout.setColumnGroup(new int [] {2, 4, 8, 10});

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Produção:", cc.xy(2, 2));
		builder.add(textCodigo, cc.xy(4, 2));

		builder.addLabel("Data:", cc.xy(8, 2));
		builder.add(dateProducao, cc.xy(10, 2));

		builder.addSeparator("Produtos", cc.xyw(2, 4, 9));

		builder.add(barraRolagem, cc.xywh(2, 6, 9, 8));

		builder.add(montaBarraBotao(), cc.xyw( 2, 15, 7));

		return builder.getPanel();
	}

	private void pesquisaProducao() {
		ProducaoVO p = controle.getProducao(id);

		dateProducao.setDate(converteDia(p.getDataProducao()));

		p = controle.getProducao(id);
		itensTabela = controle.getProdutosProducao(id);

		atualizaTabela();
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		VisualizarProducaoVisao.this.dispose();

		if(gerenciarProducaoVisao != null) {
			gerenciarProducaoVisao.setVisible(true);
			gerenciarProducaoVisao.atualizaTabela();
		}
	}

	private void atualizaTabela() {
		tableModel.setNumRows(0);

		for(ItensProducaoVO i : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					i.getCodigoProduto(), i.getDescricaoProduto(), i.getQtd()
			});

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

	//converte String em variavel do tipo date
	public Date converteDia(String dia) {
		Date horaDate = null;
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");

		try {
			horaDate = data.parse(dia);
		} catch (ParseException e) {
		}

		return horaDate;
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonFechar).build();	
	}
}
