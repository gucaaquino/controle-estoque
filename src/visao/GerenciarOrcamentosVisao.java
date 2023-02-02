package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controle.LumanferControle;
import modelo.vo.EmpresaVO;
import modelo.vo.OrcamentoVO;

public class GerenciarOrcamentosVisao extends JFrame{
	private LumanferControle controle;
	private LumanferMenu menu;
	private AdicionarPedidoVisao adicionarPedidoVisao;

	private JFileChooser chooser;
	
	private JTextField textPesquisa;

	private JButton buttonAdicionar;
	private JButton buttonAlterar;
	private JButton buttonDeletar;
	private JButton buttonVoltar;
	private JButton buttonVisualizar;

	private JComboBox comboEmpresas;

	private JRadioButton radioCodigo;
	private JRadioButton radioEmissao;

	private ButtonGroup groupPesquisa;

	private ArrayList<OrcamentoVO> itensTabela;
	private ArrayList<EmpresaVO> empresas;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	//construtor
	public GerenciarOrcamentosVisao(LumanferControle controle, LumanferMenu menu, AdicionarPedidoVisao adicionarPedidoVisao) {
		super("Gerenciar Orçamentos");

		this.controle = controle;
		this.menu = menu;
		this.adicionarPedidoVisao = adicionarPedidoVisao;

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
						adicionar();
					}
				}
				);

		buttonAlterar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						atualizar();
					}
				}
				);

		buttonDeletar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						deletar();
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

			public void actionPerformed(ActionEvent e) {
				gerarExcel();
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
				if(e.getClickCount() == 2) {
					if(adicionarPedidoVisao != null) {
						adicionarPedidoVisao.textOrcamento.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
						adicionarPedidoVisao.implementaOrcamento();

						fechar();
					}
				}
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

		radioEmissao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});

		comboEmpresas.addActionListener(new ActionListener() {

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
		tableModel.addColumn("Validade");
		tableModel.addColumn("Empresa");
		tableModel.addColumn("Data emissão");

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
		
		table.setRowHeight(20);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		buttonVisualizar = new JButton("Gerar Excel");
		buttonAdicionar = new JButton("Adicionar");
		buttonAlterar = new JButton("Alterar");
		buttonDeletar = new JButton("Excluir");
		buttonVoltar = new JButton("Voltar");

		buttonAdicionar.setToolTipText("Adicionar novo orçamento");
		buttonAlterar.setToolTipText("Alterar orçamento");
		buttonDeletar.setToolTipText("Deletar orçamento");
		buttonVoltar.setToolTipText("Voltar a tela inicial");
		buttonVisualizar.setToolTipText("Visualizar orçamento");

		buttonAlterar.setEnabled(false);
		buttonDeletar.setEnabled(false);
		buttonVisualizar.setEnabled(false);

		radioCodigo = new JRadioButton("Codigo");
		radioEmissao = new JRadioButton("Data");

		groupPesquisa = new ButtonGroup();
		groupPesquisa.add(radioCodigo);
		groupPesquisa.add(radioEmissao);

		radioCodigo.setSelected(true);

		textPesquisa = new JTextField();

		itensTabela = new ArrayList<OrcamentoVO>();

		comboEmpresas = new JComboBox();
		comboEmpresas.addItem("Qualquer empresa");

		empresas = controle.getEmpresas("", "");

		for(EmpresaVO e : empresas) {
			comboEmpresas.addItem(e.getDescricao());
		}
		
		chooser = new JFileChooser();

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls");

		chooser.resetChoosableFileFilters();
		chooser.setFileFilter(filter);		
		chooser.setApproveButtonText("Gerar Excel");

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
		builder.add(radioEmissao, cc.xy(6, 6));

		builder.addLabel("Empresa:", cc.xy(8, 4));
		builder.add(comboEmpresas, cc.xy(8, 6));

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

		String empresaSelecionada = comboEmpresas.getSelectedItem().toString();

		if(empresaSelecionada == "Qualquer empresa")
			empresaSelecionada = "";

		if(radioCodigo.isSelected()) 
			itensTabela = controle.getOrcamentos(pesquisa, "", empresaSelecionada);
		else
			itensTabela = controle.getOrcamentos("", pesquisa, empresaSelecionada);

		for(OrcamentoVO o : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					o.getId(), o.getValProposta(), o.getEmpresa(), o.getEmissao()
			});
		}
	}

	private void fechar() {
		GerenciarOrcamentosVisao.this.dispose();

		if(menu != null) {
			menu.atualizaEmpresas();
			menu.atualizaTabela();
		}if(adicionarPedidoVisao != null)
			adicionarPedidoVisao.setVisible(true);
	}

	private void adicionar() {
		AdicionarOrcamentoVisao v;

		v = new AdicionarOrcamentoVisao(controle, GerenciarOrcamentosVisao.this);

		v.setSize(850, 570);
		v.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		v.setResizable(true);
		v.setLocationRelativeTo(null);
		v.getContentPane().setBackground(Color.WHITE);
		v.setVisible(true);

		this.setVisible(false);

	}

	private void atualizar() {
		AtualizaOrcamentoVisao a;

		if(table.getSelectedRowCount() == 1) {
			a = new AtualizaOrcamentoVisao(controle, GerenciarOrcamentosVisao.this, Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));

			a.setSize(830, 550);
			a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			a.setResizable(true);
			a.setLocationRelativeTo(null);
			a.getContentPane().setBackground(Color.WHITE);
			a.setVisible(true);

			this.setVisible(false);
		}
	}

	private void deletar() {
		DeletarOrcamentoVisao d;

		if(table.getSelectedRowCount() == 1) {
			d = new DeletarOrcamentoVisao(controle, GerenciarOrcamentosVisao.this, Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));

			d.setSize(830, 550);
			d.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			d.setResizable(true);
			d.setLocationRelativeTo(null);
			d.getContentPane().setBackground(Color.WHITE);
			d.setVisible(true);

			this.setVisible(false);
		}
	}

	private void gerarExcel() {
		OrcamentoVO o;
		
		int ret = chooser.showOpenDialog(this);

		if(ret == JFileChooser.APPROVE_OPTION) {
			String caminho;
			File file = chooser.getSelectedFile();

			GerarExcelPedidoVisao g;

			if(!(file.getName() == "")) {
				if(file.getName().contains(".xls")) 
					caminho = file.getPath();

				else 
					caminho =file.getPath() + ".xls";
				
				o = controle.getOrcamento(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
				o.setPedido("");	
				o.setEmissao("");
				o.setValProposta(o.getValProposta());

				g = new GerarExcelPedidoVisao(o, caminho);
				if(g.geraExcel()) {
					JOptionPane.showMessageDialog(GerenciarOrcamentosVisao.this, String.format("Excel gerado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
					
				}else
					JOptionPane.showMessageDialog(GerenciarOrcamentosVisao.this, String.format("Erro ao gerar Excel"), "", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonVoltar, buttonAdicionar, buttonAlterar, buttonDeletar, buttonVisualizar).build();	
	}
}
