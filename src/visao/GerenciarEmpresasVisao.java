package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controle.LumanferControle;
import modelo.vo.EmpresaVO;

public class GerenciarEmpresasVisao extends JFrame{
	private LumanferControle controle;
	private LumanferMenu menu;

	private JTextField textPesquisa;

	private JButton buttonAdicionar;
	private JButton buttonAlterar;
	private JButton buttonDeletar;
	private JButton buttonVoltar;
	private JButton buttonVisualizar;

	private JRadioButton radioCodigo;
	private JRadioButton radioNome;

	private ButtonGroup groupPesquisa;

	private ArrayList<EmpresaVO> itensTabela;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	//construtor
	public GerenciarEmpresasVisao(LumanferControle controle, LumanferMenu menu) {
		super("Gerenciar Empresas");

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

		buttonAdicionar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						adicionarEmpresa();
					}
				}
				);

		buttonAlterar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						atualizarEmpresa();
					}
				}
				);

		buttonDeletar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						deletarEmpresa();
					}
				}
				);

		buttonVoltar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						fechar();
					}
				}
				);
		
		buttonVisualizar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				visualizarEmpresa();
			}
		});

		table.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if(table.getSelectedRowCount() == 1) {
					buttonAlterar.setEnabled(true);
					buttonDeletar.setEnabled(true);
					buttonVisualizar.setEnabled(true);
				}
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseClicked(MouseEvent e) {

			}
		});

		textPesquisa.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				atualizaTabela();
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		radioCodigo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});

		radioNome.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
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
		tableModel.addColumn("Nome");

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
		
		table.setRowHeight(20);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		buttonVisualizar = new JButton("Visualizar");
		buttonAdicionar = new JButton("Adicionar");
		buttonAlterar = new JButton("Alterar");
		buttonDeletar = new JButton("Excluir");
		buttonVoltar = new JButton("Voltar");

		buttonAdicionar.setToolTipText("Adicionar nova empresa");
		buttonAlterar.setToolTipText("Alterar empresa");
		buttonDeletar.setToolTipText("Deletar empresa");
		buttonVoltar.setToolTipText("Voltar a tela inicial");
		buttonVisualizar.setToolTipText("Visualizar empresa");

		buttonAlterar.setEnabled(false);
		buttonDeletar.setEnabled(false);
		buttonVisualizar.setEnabled(false);

		radioCodigo = new JRadioButton("Codigo");
		radioNome = new JRadioButton("Nome");

		groupPesquisa = new ButtonGroup();
		groupPesquisa.add(radioCodigo);
		groupPesquisa.add(radioNome);

		radioCodigo.setSelected(true);

		textPesquisa = new JTextField();

		itensTabela = new ArrayList<EmpresaVO>();

		atualizaTabela();
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu, right:p, 5dlu, 100dlu:grow, 5dlu, p, 5dlu, p, 10dlu",
				"10dlu, p, 5dlu, p, 2dlu, p, 5dlu, 200dlu:grow, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.add(barraRolagem, cc.xyw(2, 8, 7));

		builder.add(montaBarraBotao(), cc.xyw(4, 2, 5));

		builder.addLabel("Pesquisa:", cc.xy(2, 6));
		builder.add(textPesquisa, cc.xy(4, 6));

		builder.add(radioCodigo, cc.xy(6, 4));
		builder.add(radioNome, cc.xy(6, 6));

		return builder.getPanel();
	}

	public void atualizaTabela() {
		buttonAlterar.setEnabled(false);
		buttonDeletar.setEnabled(false);
		buttonVisualizar.setEnabled(false);
		
		tableModel.setNumRows(0);

		String pesquisa = textPesquisa.getText();

		if(pesquisa == null)
			pesquisa = "";

		if(radioCodigo.isSelected()) 
			itensTabela = controle.getEmpresas(pesquisa, "");
		else
			itensTabela = controle.getEmpresas("", pesquisa);

		for(EmpresaVO e : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					e.getId(), e.getDescricao()
			});
		}
	}

	private void fechar() {
		GerenciarEmpresasVisao.this.dispose();

		if(menu != null) {
			menu.atualizaEmpresas();
			menu.atualizaTabela();
		}
	}

	private void adicionarEmpresa() {
		AdicionarEmpresaVisao a;

		a = new AdicionarEmpresaVisao(controle, GerenciarEmpresasVisao.this);

		a.setSize(480, 300);
		a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		a.setResizable(true);
		a.setLocationRelativeTo(null);
		a.getContentPane().setBackground(Color.WHITE);
		a.setVisible(true);

		this.setVisible(false);
	}

	private void atualizarEmpresa() {
		AtualizaEmpresaVisao a;

		if(table.getSelectedRowCount() == 1) {
			a = new AtualizaEmpresaVisao(controle, GerenciarEmpresasVisao.this, table.getValueAt(table.getSelectedRow(), 0).toString());

			a.setSize(480, 300);
			a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			a.setResizable(true);
			a.setLocationRelativeTo(null);
			a.getContentPane().setBackground(Color.WHITE);
			a.setVisible(true);

			this.setVisible(false);
		}
	}

	private void deletarEmpresa() {
		DeletarEmpresaVisao d;

		if(table.getSelectedRowCount() == 1) {
			d = new DeletarEmpresaVisao(controle, GerenciarEmpresasVisao.this, table.getValueAt(table.getSelectedRow(), 0).toString());

			d.setSize(480, 300);
			d.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			d.setResizable(true);
			d.setLocationRelativeTo(null);
			d.getContentPane().setBackground(Color.WHITE);
			d.setVisible(true);

			this.setVisible(false);
		}
	}
	
	private void visualizarEmpresa() {
		VisualizarEmpresaVisao v;

		if(table.getSelectedRowCount() == 1) {
			v = new VisualizarEmpresaVisao(controle, GerenciarEmpresasVisao.this, table.getValueAt(table.getSelectedRow(), 0).toString());

			v.setSize(480, 300);
			v.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			v.setResizable(true);
			v.setLocationRelativeTo(null);
			v.getContentPane().setBackground(Color.WHITE);
			v.setVisible(true);

			this.setVisible(false);
		}
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonVoltar, buttonAdicionar, buttonAlterar, buttonDeletar, buttonVisualizar).build();	
	}
}
