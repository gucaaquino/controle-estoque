package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controle.LumanferControle;
import modelo.vo.LocalizacaoVO;
import modelo.vo.ProdutoVO;
import visao.AdicionarPedidoVisao.TableRenderer;

public class GerenciarProdutosVisao extends JFrame{
	private LumanferControle controle;

	private LumanferMenu menu;
	private AdicionarPedidoVisao adicionaPedidoVisao;
	private AtuDelPedidoVisao atualizaPedidoVisao;
	private AdicionarProducaoVisao adicionarProducaoVisao;
	private AtualizaProducaoVisao atualizaProducaoVisao;
	private GerarRelatorioABCVisao gerarRelatorioABCVisao;
	private AdicionarOrcamentoVisao adicionarOrcamentoVisao;
	private AtualizaOrcamentoVisao atualizarOrcamentoVisao;

	private JTextField textPesquisa;

	private JButton buttonAdicionarProduto;
	private JButton buttonAlterarProduto;
	private JButton buttonDeletarProduto;
	private JButton buttonVoltar;

	private JComboBox comboLocais;
	private JComboBox comboPesquisa;

	private ArrayList<ProdutoVO> itensTabela;
	private ArrayList<LocalizacaoVO> arrayLocais;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	//construtor
	public GerenciarProdutosVisao(LumanferControle controle, LumanferMenu menu, AdicionarPedidoVisao adicionaPedidoVisao, AtuDelPedidoVisao atualizaPedidoVisao, AdicionarProducaoVisao adicionarProducaoVisao, AtualizaProducaoVisao atualizaProducaoVisao, GerarRelatorioABCVisao gerarRelatorioABCVisao, AdicionarOrcamentoVisao adicionarOrcamentoVisao, AtualizaOrcamentoVisao atualizarOrcamentoVisao) {
		super("Gerenciar Produtos");

		this.controle = controle;
		this.menu = menu;
		this.adicionaPedidoVisao = adicionaPedidoVisao;
		this.atualizaPedidoVisao = atualizaPedidoVisao;
		this.adicionarProducaoVisao = adicionarProducaoVisao;
		this.atualizaProducaoVisao = atualizaProducaoVisao;
		this.gerarRelatorioABCVisao = gerarRelatorioABCVisao;
		this.adicionarOrcamentoVisao = adicionarOrcamentoVisao;
		this.atualizarOrcamentoVisao = atualizarOrcamentoVisao;

//		try { 
//
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	
//		} 
//		catch (Exception e) 
//		{ }

		criaJTable();

		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonAdicionarProduto.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						adicionarProduto();
					}
				}
				);

		buttonAlterarProduto.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						atualizarProduto();
					}
				}
				);

		buttonDeletarProduto.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						deletarProduto();
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

		table.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if(table.getSelectedRowCount() == 1) {
					buttonAlterarProduto.setEnabled(true);
					buttonDeletarProduto.setEnabled(true);
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

					if(adicionaPedidoVisao != null) {
						adicionaPedidoVisao.textDescricao.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
						adicionaPedidoVisao.textProduto.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
						adicionaPedidoVisao.textCodAlternativo.setText(controle.getCodigoAlternativo(table.getValueAt(table.getSelectedRow(), 1).toString()));

						fechar();

					}else if(atualizaPedidoVisao != null) {
						atualizaPedidoVisao.textDescricao.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
						atualizaPedidoVisao.textProdutos.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
						atualizaPedidoVisao.textCodAlternativo.setText(controle.getCodigoAlternativo(table.getValueAt(table.getSelectedRow(), 1).toString()));

						fechar();

					}else if(adicionarProducaoVisao != null) {
						adicionarProducaoVisao.textDescricao.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
						adicionarProducaoVisao.textProduto.setText(table.getValueAt(table.getSelectedRow(), 1).toString());

						fechar();

					}else if(atualizaProducaoVisao != null) {
						atualizaProducaoVisao.textDescricao.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
						atualizaProducaoVisao.textProduto.setText(table.getValueAt(table.getSelectedRow(), 1).toString());

						fechar();

					}else if(gerarRelatorioABCVisao != null) {
						gerarRelatorioABCVisao.textCodigo.setText(table.getValueAt(table.getSelectedRow(), 1).toString());

						fechar();
					}else if(adicionarOrcamentoVisao != null) {
						adicionarOrcamentoVisao.textDescricao.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
						adicionarOrcamentoVisao.textProduto.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
						adicionarOrcamentoVisao.textCodAlternativo.setText(controle.getCodigoAlternativo(table.getValueAt(table.getSelectedRow(), 1).toString()));
						adicionarOrcamentoVisao.textValorProd.setText(table.getValueAt(table.getSelectedRow(), 6).toString());

						fechar();

					}else if(atualizarOrcamentoVisao != null) {
						atualizarOrcamentoVisao.textDescricao.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
						atualizarOrcamentoVisao.textProduto.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
						atualizarOrcamentoVisao.textCodAlternativo.setText(controle.getCodigoAlternativo(table.getValueAt(table.getSelectedRow(), 1).toString()));
						atualizarOrcamentoVisao.textValorProd.setText(table.getValueAt(table.getSelectedRow(), 6).toString());

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

		comboPesquisa.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});

		comboLocais.addActionListener(new ActionListener() {

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
		tableModel.addColumn("Registro");
		tableModel.addColumn("Cód. Desenho");
		tableModel.addColumn("Cód. Alt");
		tableModel.addColumn("Revisão");
		tableModel.addColumn("Descrição");
		tableModel.addColumn("Qtd");
		tableModel.addColumn("Valor");
		tableModel.addColumn("Local");

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);	
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(40);
		table.getColumnModel().getColumn(7).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);

		table.setDefaultRenderer(Object.class, new TableRenderer());

		table.setRowHeight(20);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		buttonAdicionarProduto = new JButton("Adicionar");
		buttonAlterarProduto = new JButton("Alterar");
		buttonDeletarProduto = new JButton("Excluir");
		buttonVoltar = new JButton("Voltar");

		buttonAdicionarProduto.setToolTipText("Adicionar novo produto");
		buttonAlterarProduto.setToolTipText("Alterar produto");
		buttonDeletarProduto.setToolTipText("Deletar produto");
		buttonVoltar.setToolTipText("Voltar a tela inicial");

		buttonAlterarProduto.setEnabled(false);
		buttonDeletarProduto.setEnabled(false);
		
		comboLocais = new JComboBox();
		comboPesquisa = new JComboBox();

		comboLocais.addItem("...");

		comboPesquisa.addItem("Cód. Desenho");
		comboPesquisa.addItem("Cód. Alt");
		comboPesquisa.addItem("Nome");

		arrayLocais = controle.getLocalizacoes("", "");

		for(LocalizacaoVO l : arrayLocais)
			comboLocais.addItem(l.getDescricao());

		textPesquisa = new JTextField();

		itensTabela = new ArrayList<ProdutoVO>();

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

		builder.add(comboPesquisa, cc.xy(6, 6));

		builder.addLabel("Locais:", cc.xy(8, 4));
		builder.add(comboLocais, cc.xy(8, 6));

		return builder.getPanel();
	}

	public void atualizaTabela() {
		buttonAlterarProduto.setEnabled(false);
		buttonDeletarProduto.setEnabled(false);

		tableModel.setNumRows(0);

		String pesquisa = textPesquisa.getText();

		if(pesquisa == null)
			pesquisa = "";

		String local = comboLocais.getSelectedItem().toString();

		if(local.equals("..."))
			local = "";

		if(comboPesquisa.getSelectedIndex() == 0) 
			itensTabela = controle.getProdutos(pesquisa, "", "", local);
		else if(comboPesquisa.getSelectedIndex() == 2)
			itensTabela = controle.getProdutos("", pesquisa, "", local);
		else 
			itensTabela = controle.getProdutos("", "", pesquisa, local);

		for(ProdutoVO p : itensTabela) {
			//as linhas da tabela devem ser do tipo Object

			Locale localeBR = new Locale("pt","BR");
			NumberFormat numberFormat = NumberFormat.getNumberInstance(localeBR);
			String codALt = p.getCodAlternativo();

			if(ehNumero(codALt)) {
				codALt = codALt.replace(".", "");
				codALt = numberFormat.format(Integer.parseInt(codALt));
			}

			tableModel.addRow(new Object[] {
					p.getId(), p.getCodigo(), codALt, p.getRevisao(), p.getDescricao(), p.getQtd(), p.getValor(), p.getLocalizacao()
			});
		}
	}

	private void fechar() {
		GerenciarProdutosVisao.this.dispose();

		if(adicionaPedidoVisao != null)
			adicionaPedidoVisao.setVisible(true);
		else if(atualizaPedidoVisao != null)
			atualizaPedidoVisao.setVisible(true);
		else if(adicionarProducaoVisao != null)
			adicionarProducaoVisao.setVisible(true);
		else if(atualizaProducaoVisao != null)
			atualizaProducaoVisao.setVisible(true);
		else if(gerarRelatorioABCVisao != null)
			gerarRelatorioABCVisao.setVisible(true);
		else if(adicionarOrcamentoVisao != null)
			adicionarOrcamentoVisao.setVisible(true);
		else if(atualizarOrcamentoVisao != null)
			atualizarOrcamentoVisao.setVisible(true);
		else if(menu != null) {
			menu.atualizaEmpresas();
			menu.atualizaTabela();
		}

	}

	private void visuzalizarProduto() {
		if(table.getSelectedRowCount() == 1) {

			String caminho = "";

			try {
				FileReader arq = new FileReader("./setup.txt");
				BufferedReader lerArq = new BufferedReader(arq);

				caminho = lerArq.readLine(); 

				arq.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(GerenciarProdutosVisao.this, String.format("Verifique o caminho da pasta dos desenhos no arquivo setup.txt"), "", JOptionPane.ERROR_MESSAGE);
			}

			BuscaArquivo b = new BuscaArquivo();
			ArrayList<String> lista = b.buscarArquivoPorNome(table.getValueAt(table.getSelectedRow(), 1).toString(), caminho);

			if(lista.size() == 0)
				JOptionPane.showMessageDialog(GerenciarProdutosVisao.this, String.format("Erro ao pesquisar desenho"), "", JOptionPane.ERROR_MESSAGE);
			else {
				try {
					Runtime.getRuntime().exec("explorer " + lista.get(0));
				} catch (IOException e) {

				}
			}
		}
	}

	private void adicionarProduto() {
		AdicionarProdutoVisao a = new AdicionarProdutoVisao(controle, GerenciarProdutosVisao.this);
		a.setSize(740, 440);
		a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		a.setResizable(true);
		a.setLocationRelativeTo(null);
		a.getContentPane().setBackground(Color.WHITE);
		a.setVisible(true);

		this.setVisible(false);
	}

	private void atualizarProduto() {
		AtualizaProdutoVisao a;

		if(table.getSelectedRowCount() == 1) {
			a = new AtualizaProdutoVisao(controle, tableModel.getValueAt(table.getSelectedRow(), 1).toString(), GerenciarProdutosVisao.this);

			a.setSize(740, 440);
			a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			a.setResizable(true);
			a.setLocationRelativeTo(null);
			a.getContentPane().setBackground(Color.WHITE);
			a.setVisible(true);

			this.setVisible(false);
		}
	}

	private void deletarProduto() {
		DeletarProdutoVisao d;

		if(table.getSelectedRowCount() == 1) {
			d = new DeletarProdutoVisao(controle, tableModel.getValueAt(table.getSelectedRow(), 1).toString(), GerenciarProdutosVisao.this);

			d.setSize(740, 440);
			d.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			d.setResizable(true);
			d.setLocationRelativeTo(null);
			d.getContentPane().setBackground(Color.WHITE);
			d.setVisible(true);

			this.setVisible(false);
		}
	}

	private boolean ehNumero(String numero) {
		boolean ehNumero = true;

		try {
			float n = Float.parseFloat(numero);
		}catch(NumberFormatException e){
			ehNumero = false;
		}

		return ehNumero;
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonVoltar, buttonAdicionarProduto, buttonAlterarProduto, buttonDeletarProduto).build();	
	}

	public class TableRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if(row % 2 == 0) 
				comp.setBackground(Color.WHITE);
			else 
				comp.setBackground(new Color(242, 242, 242));
				


			if(column == 3 || column == 5 || column == 7) {
				comp.setForeground(Color.RED);
				comp.setFont(new Font("Arial", Font.BOLD, 12));
			}else {
				comp.setForeground(Color.BLACK);
				comp.setFont(new Font("Arial", Font.PLAIN, 12));
			}

			return comp;
		}
	}
}
