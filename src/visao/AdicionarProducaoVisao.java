package visao;

import java.awt.Color;
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
import javax.swing.JOptionPane;
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
import com.toedter.components.JSpinField;

import controle.LumanferControle;
import modelo.vo.ItensProducaoVO;
import modelo.vo.ProducaoVO;

public class AdicionarProducaoVisao extends JFrame{
	private LumanferControle controle;
	private GerenciarProducaoVisao gerenciarProducaoVisao;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private JSpinField spinQtd;

	public JTextField textDescricao;
	public JTextField textProduto;

	private JDateChooser dateProducao;

	private JButton buttonAddProd;
	private JButton buttonDelProd;
	private JButton buttonAdicionar;
	private JButton buttonLimpar;
	private JButton buttonFechar;
	private JButton buttonPesqProd;

	private ImageIcon iconAdd;
	private ImageIcon iconLimpar;
	private ImageIcon iconVoltar;

	private ArrayList<ItensProducaoVO> itensTabela;

	//construtor
	public AdicionarProducaoVisao(LumanferControle controle, GerenciarProducaoVisao gerenciarProducaoVisao) {
		super("Adicionar Produção");

		this.controle = controle;
		this.gerenciarProducaoVisao = gerenciarProducaoVisao;

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

		buttonAddProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarProduto();
			}
		});

		buttonDelProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarProduto();
			}
		});

		buttonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarPedido();
			}
		});

		buttonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		buttonPesqProd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pesquisaProduto();
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
		
		table.setRowHeight(20);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		spinQtd = new JSpinField();
		spinQtd.setMinimum(1);
		spinQtd.setValue(1);

		textDescricao = new JTextField(15);
		textProduto = new JTextField(15);

		buttonAddProd = new JButton("+");
		buttonAdicionar = new JButton("");
		buttonDelProd = new JButton("-");
		buttonLimpar = new JButton("");
		buttonFechar = new JButton("");
		buttonPesqProd = new JButton("...");

		iconAdd = new ImageIcon("./icones/salvar.png");
		iconLimpar = new ImageIcon("./icones/clean.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAdicionar.setIcon(iconAdd);
		buttonLimpar.setIcon(iconLimpar);
		buttonFechar.setIcon(iconVoltar);

		dateProducao = new JDateChooser();
		dateProducao.setDate(new Date());

		itensTabela = new ArrayList<ItensProducaoVO>();

		textProduto.setEditable(false);
		textDescricao.setEditable(false);
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:p, 5dlu, p, 5dlu, p, 5dlu, right:p, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 100dlu, 15dlu, p, 10dlu:grow");

		layout.setColumnGroup(new int [] {2, 4, 8, 10});

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Produção:", cc.xy(2, 2));
		builder.addLabel("...", cc.xy(4, 2));

		builder.addLabel("Data:", cc.xy(8, 2));
		builder.add(dateProducao, cc.xy(10, 2));

		builder.addSeparator("Produtos", cc.xyw(2, 4, 9));

		builder.addLabel("Produto:", cc.xy(2, 6));
		builder.add(textProduto, cc.xy(4, 6));
		builder.add(buttonPesqProd, cc.xy(6, 6));

		builder.addLabel("Quantidade:", cc.xy(8, 6));
		builder.add(spinQtd, cc.xy(10, 6));

		builder.addLabel("Descrição:", cc.xy(2, 8));
		builder.add(textDescricao, cc.xyw(4, 8, 7));

		builder.add(barraRolagem, cc.xywh(2, 10, 9, 4));

		builder.add(buttonAddProd, cc.xy(12, 10));
		builder.add(buttonDelProd, cc.xy(12, 12));

		builder.add(montaBarraBotao(), cc.xyw( 2, 15, 7));

		return builder.getPanel();
	}

	//metodo responsavel por limpar todos os campos
	private void limpar() {
		textDescricao.setText("");
		textProduto.setText("");

		spinQtd.setValue(1);

		dateProducao.setDate(new Date());

		itensTabela.clear();

		atualizaTabela();
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AdicionarProducaoVisao.this.dispose();

		if(gerenciarProducaoVisao != null) {
			gerenciarProducaoVisao.setVisible(true);
			gerenciarProducaoVisao.atualizaTabela();
		}
	}

	private void adicionarPedido() {

		ProducaoVO p = new ProducaoVO();

		p.setDataProducao(new SimpleDateFormat("yyyy-MM-dd").format(dateProducao.getDate()));

		int id = controle.adicionaProducao(p, itensTabela);

		if(id != 0) {
			JOptionPane.showMessageDialog(AdicionarProducaoVisao.this, String.format("Produção " + id + " cadastrada com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
			fechar();
		}else
			JOptionPane.showMessageDialog(AdicionarProducaoVisao.this, String.format("Erro ao cadastrar produção"), "", JOptionPane.ERROR_MESSAGE);
	}

	private void deletarProduto() {
		int linhaSelecionada = table.getSelectedRow();

		if(table.getSelectedRowCount() == 1) {
			itensTabela.remove(linhaSelecionada);
			atualizaTabela();
		}
	}

	private void pesquisaProduto() {
		GerenciarProdutosVisao g = new GerenciarProdutosVisao(controle, null, null, null, AdicionarProducaoVisao.this, null, null, null, null);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);

		this.setVisible(false);

	}

	private void adicionarProduto() {	
		if(!textProduto.getText().equals("")) {
			ItensProducaoVO item = new ItensProducaoVO();
			item.setCodigoProduto(textProduto.getText());
			item.setDescricaoProduto(textDescricao.getText());
			item.setIdProduto(controle.getIdProduto(item.getCodigoProduto()));
			item.setQtd(spinQtd.getValue());

			itensTabela.add(item);

			atualizaTabela();
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

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAdicionar, buttonLimpar, buttonFechar).build();	
	}
}
